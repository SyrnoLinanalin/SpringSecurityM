package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> allUsers();
    User getById(Long id);
    void save(User user);
    void update(User user);
    void delete(User user);
    Optional<User> getUserByName(String username);
}
