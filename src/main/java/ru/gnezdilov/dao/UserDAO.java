package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Optional;

@Component
public class UserDAO {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public UserModel insert(String name, String email, String password) {
        try {
            Optional<UserModel> userCheck = em.createNamedQuery("User.findByNameAndEmail", UserModel.class)
                    .setParameter("name", name)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
            if (!userCheck.isPresent()) {
                UserModel user = new UserModel();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                em.persist(user);
                return user;
            } else {
                throw new AlreadyExistsException("Email already exists");
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public Optional<UserModel> findByEmailAndPassword(String email, String password) {
        try {
            return Optional.of(em.createNamedQuery("User.findByEmailAndPassword", UserModel.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public UserModel findById(int id) {
        try {
            UserModel user = em.find(UserModel.class, id);
            if (user != null) {
                return user;
            } else {
                throw new NotFoundException("User not found");
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public boolean existsById(int id) {
        try {
            UserModel user = em.find(UserModel.class, id);
            return user != null;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
