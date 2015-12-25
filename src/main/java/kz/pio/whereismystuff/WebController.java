package kz.pio.whereismystuff;

import kz.pio.whereismystuff.common.Util;
import kz.pio.whereismystuff.domain.Answer;
import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.domain.Question;
import kz.pio.whereismystuff.domain.User;
import kz.pio.whereismystuff.service.AnswerService;
import kz.pio.whereismystuff.service.CategoryService;
import kz.pio.whereismystuff.service.QuestionService;
import kz.pio.whereismystuff.service.UserService;
import kz.pio.whereismystuff.service.auth.GoogleAuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Application controller
 * @version 20140614
 * @author Rustem S
 */
@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoogleAuthService googleAuthService;

    @ModelAttribute("categories")
    public Set<Category> populateCategories() {
        return this.categoryService.findAll();
    }

    @ModelAttribute("auth")
    public boolean getAuth() {
        return Util.userAuthenticated();
    }

    @RequestMapping("/")
    public String showQuestions(@RequestParam(value="category", required=false) final String categoryName,
                                @RequestParam(value="code", required=false) final String code,
                                @RequestParam(value="state", required=false) final String state,
                                final Model model) {
        if (code != null && state != null && state.equals(Util.session().getAttribute("state"))) { // check callback from Google
            String userJson = googleAuthService.getUserInfoJson(code); // create google user in db
            String username = Util.getElementFromJson(userJson, "name");
            User user = userService.getUserByName(username);
            if (user == null) { // if user not exist in db, then create
                user = new User();
                user.setUsername(username);
                user.setEmail(Util.getElementFromJson(userJson, "email"));
                userService.save(user);
            }
            Util.authenticateUser(username); // authenticate
            Util.session().removeAttribute("state"); // clear "state" in session
        }

        List<Question> questions; // populate Question entities
        if (categoryName == null) {
            questions = questionService.findAll();
        } else {
            Set<Category> categories = new HashSet<Category>();
            Category category = categoryService.getCategoryByName(categoryName);
            categories.add(category);
            questions = questionService.findByCategories(categories);
        }
        Collections.sort(questions);
        model.addAttribute("questions", questions);
        return "questions";
    }

    @RequestMapping("/question")
    public String showQuestionForm(final Model model) {
        Question question = new Question();
        model.addAttribute("question", question);
        return "question";
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public String processAddQuestion(@Valid @ModelAttribute(value="question") Question question, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            return "question";
        }
        question.setCreateDate(new Date());
        User user = userService.getUserByName(Util.getAuthenticatedUsername()); // get current session user for new Question entity
        question.setUser(user);
        questionService.save(question);
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "redirect:/";
    }

    @RequestMapping(value = "/question/{id}")
    public String showQuestionFormById(@PathVariable final String id, final Model model) {
        Question question = questionService.findById(Long.parseLong(id)); // find Question by ID
        model.addAttribute("question", question); // send Question to view
        Answer answer = new Answer();
        model.addAttribute("answer", answer); // initialize new Answer and send it to view
        return "answer";
    }

    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
    public String processAddAnswer(@Valid @ModelAttribute(value="answer") Answer answer, final BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) { // if validation errors exist
            Question question =  answer.getQuestion();
            model.addAttribute("question", question);
            return "answer";
        }
        answer.setCreateDate(new Date());
        User user = userService.getUserByName(Util.getAuthenticatedUsername()); // get current session user for new Answer entity
        answer.setUser(user);
        Question question =  answer.getQuestion();
        answerService.save(answer);
        return "redirect:/question/" + question.getId();
    }

    @RequestMapping(value = "/users/{username}")
    public String showUserViewFormByName(@PathVariable final String username, final Model model) {
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        String picture;
        if (user.getPicture() == null)
            picture = Util.NO_PHOTO_BASE64; // user have not photo
        else
            picture = new String(Base64.encodeBase64(user.getPicture()));
        model.addAttribute("pictureBase64", picture);
        model.addAttribute("canEdit", username.equals(Util.getAuthenticatedUsername())); // user can only edit own profile
        return "userview";
    }

    @RequestMapping(value = "/users/{username}/edit")
    public String showUserEditFormByName(@PathVariable final String username, final Model model) {
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);
        String picture;
        if (user.getPicture() == null)
            picture = Util.NO_PHOTO_BASE64; // user have not photo
        else
            picture = new String(Base64.encodeBase64(user.getPicture()));
        model.addAttribute("pictureBase64", picture);
        return "useredit";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String processUpdateUser(@Valid @ModelAttribute(value="user") User user, final BindingResult bindingResult, final Model model) {
        User oldUser = userService.findById(user.getId());
        if (bindingResult.hasErrors()) { // when there is a validation error, then return user profile edit page
            oldUser.setUsername(user.getUsername());
            oldUser.setEmail(user.getEmail());
            model.addAttribute("user", oldUser);
            model.addAttribute("pictureBase64", new String(Base64.encodeBase64(oldUser.getPicture())));
            return "useredit";
        }
        user.setPsw(oldUser.getPsw());
        user.setPicture(oldUser.getPicture());
        userService.save(user);
        return "redirect:/users/" + user.getUsername();
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String processCreateUser(@Valid @ModelAttribute(value="user") User user, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) { // when there is a validation error, then return login page
            return "login";
        }
        User newUser = userService.save(user);
        if (newUser != null) {
            Util.authenticateUser(newUser.getUsername()); // authenticate created user
        }
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String showLogin(final Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(final Model model) {
        model.addAttribute("loginError", true);
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(value = "/google_sign")
    public String processSignInWithGoogle() {
        Util.session().setAttribute("state", googleAuthService.getStateToken()); // initializing parameters for Google authentication
        return "redirect:" + googleAuthService.buildLoginUrl(); // redirect to Google authentication API
    }
}