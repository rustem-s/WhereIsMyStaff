package kz.pio.whereismystuff.web.conversion;

import kz.pio.whereismystuff.domain.Question;
import kz.pio.whereismystuff.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Question objects formatter (for Thymeleaf framework using)
 * @version 20140614
 * @author Rustem S
 */
public class QuestionFormatter implements Formatter<Question> {

    @Autowired
    private QuestionService questionService;

    public QuestionFormatter() {
        super();
    }
    public Question parse(final String text, final Locale locale) throws ParseException {
        final Long id = Long.valueOf(text);
        return this.questionService.findById(id);
    }
    public String print(final Question object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}
