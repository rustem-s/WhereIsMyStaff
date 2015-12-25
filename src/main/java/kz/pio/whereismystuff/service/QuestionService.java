package kz.pio.whereismystuff.service;

import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.domain.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    public List<Question> findAll();
    public Question findById(Long id);
    public List<Question> findByCategories(Set<Category> categories);
    public Question save(Question question);
}
