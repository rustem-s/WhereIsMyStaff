package kz.pio.whereismystuff.service.jpa;

import com.google.common.collect.Sets;
import kz.pio.whereismystuff.domain.User;
import kz.pio.whereismystuff.repository.UserRepository;
import kz.pio.whereismystuff.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service for User entity
 * @version 20140614
 * @author Rustem S
 */
@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Set<User> findAll() {
        return Sets.newHashSet(userRepository.findAll());
    }
    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }
    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByUsername(name);
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
