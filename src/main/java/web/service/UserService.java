package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    User getById(Long id);
    void save(User user, String[] roles);
    void update(User user, String[] roles);
    void delete(User user);
}

