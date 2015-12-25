package kz.pio.whereismystuff.repository;

import kz.pio.whereismystuff.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User getUserByUsername(String username);
}
