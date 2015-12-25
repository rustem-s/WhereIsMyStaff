package kz.pio.whereismystuff.repository;

import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.domain.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    public List<Question> findByCategories(Set<Category> categories);
}
