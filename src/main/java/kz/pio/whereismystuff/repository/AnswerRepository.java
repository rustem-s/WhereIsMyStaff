package kz.pio.whereismystuff.repository;

import kz.pio.whereismystuff.domain.Answer;
import kz.pio.whereismystuff.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
    public int countByQuestion(Question question);
}
