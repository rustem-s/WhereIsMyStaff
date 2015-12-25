package kz.pio.whereismystuff.service;

import kz.pio.whereismystuff.domain.User;

import java.util.Set;

public interface UserService {
    public Set<User> findAll();
    public User findById(Long id);
    public User getUserByName(String name);
    public User save(User user);
}
