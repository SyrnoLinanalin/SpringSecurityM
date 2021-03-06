package web.dao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> allUsers() {
        return entityManager.createQuery("SELECT u FROM User u join fetch u.roles",  User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(entityManager.merge(user));
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        User managed = entityManager.merge(user);
        entityManager.remove(managed);
    }

    @Override
    public Optional<User> getUserByName(String username) {

         Optional<User> user = Optional.ofNullable(entityManager.createQuery("SELECT u FROM User u join fetch u.roles where u.username = :username ", User.class)
                    .setParameter("username", username)
                    .getSingleResult());
         return user;

        }
    }

