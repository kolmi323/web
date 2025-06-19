package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class UserDAO extends DAO {

    public UserDAO(DataSource ds, EntityManagerFactory emf) {
        super(ds, emf);
    }

    @Transactional
    public UserModel insert(String name, String email, String password) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            UserModel user = new UserModel();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            em.persist(user);
            tx.commit();
            if (user.getId() != 0) {
                return user;
            } else {
                throw new DAOException("Insert user failed");
            }
        } catch (PersistenceException e) {
            if (isUniqueSQLState(e)) {
                throw new AlreadyExistsException("Email already exists");
            } else {
                throw new DAOException(e);
            }
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
