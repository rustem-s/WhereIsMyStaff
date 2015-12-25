package kz.pio.whereismystuff.service.jpa;

import kz.pio.whereismystuff.domain.Answer;
import kz.pio.whereismystuff.domain.Question;
import kz.pio.whereismystuff.repository.AnswerRepository;
import kz.pio.whereismystuff.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Answer entity
 * @version 20140614
 * @author Rustem S
 */
@Service("answerService")
@Repository
@Transactional
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
