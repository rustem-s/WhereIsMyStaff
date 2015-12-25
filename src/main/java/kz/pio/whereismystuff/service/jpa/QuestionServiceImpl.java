package kz.pio.whereismystuff.service.jpa;

import com.google.common.collect.Lists;
import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.domain.Question;
import kz.pio.whereismystuff.repository.QuestionRepository;
import kz.pio.whereismystuff.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service for Question entity
 * @version 20140614
 * @author Rustem S
 */
@Service("questionService")
@Repository
@Transactional
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public List<Question> findAll() {
        return Lists.newArrayList(questionRepository.findAll());
    }
    @Override
    public Question findById(Long id) {
        return questionRepository.findOne(id);
    }
    @Override
    public List<Question> findByCategories(Set<Category> categories) {
        return questionRepository.findByCategories(categories);
    }
    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }
}
