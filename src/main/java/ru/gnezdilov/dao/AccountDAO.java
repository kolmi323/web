package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.exception.NotFoundException;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountDAO extends DAO {
    public AccountDAO(DataSource ds, EntityManagerFactory emf) {
        super(ds, emf);
    }

    public boolean existsById(int id, int userId) {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserId", AccountModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return accountModel != null;
        } catch (NoResultException e) {
            return false;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public AccountModel findById(int id, int userId) {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserId", AccountModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return accountModel;
        } catch (NoResultException e) {
            throw new NotFoundException("Account with id " + id + " not found");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public List<AccountModel> getAll(int userId) {
        try {
            return em.createNamedQuery("Account.findByUserId", AccountModel.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public AccountModel insert(int userId, String name, BigDecimal balance) {
        try {
            AccountModel accountModel = new AccountModel();
            accountModel.setUserId(userId);
            accountModel.setName(name);
            accountModel.setBalance(balance);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(accountModel);
            tx.commit();
            if (accountModel.getId() != 0) {
                return accountModel;
            } else {
                throw new DAOException("Insert account failed");
            }
        } catch (PersistenceException e) {
            if (isUniqueSQLState(e)) {
                throw new AlreadyExistsException("Account already exists");
            } else {
                throw new DAOException(e);
            }
        }
    }

    @Transactional
    public boolean delete(int id, int userId) {
        try {
            AccountModel accountModel = findById(id, userId);
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(accountModel);
            tx.commit();
            return true;
        } catch (NotFoundException e) {
            return false;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
