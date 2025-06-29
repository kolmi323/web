package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class AccountDAO {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public boolean existsById(int id, int userId) {
        try {
            Optional<AccountModel> accountModel = em.createNamedQuery("Account.findByIdAndUserId", AccountModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getResultStream()
                    .findFirst();
            return accountModel.isPresent();
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public AccountModel findById(int id, int userId) {
        try {
            return em.createNamedQuery("Account.findByIdAndUserId", AccountModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getResultStream()
                    .findFirst().orElseThrow(() -> new NotFoundException("Account with id " + id + " not found"));
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
            Optional<AccountModel> accountCheck = em.createNamedQuery("Account.findByUserIdAndName", AccountModel.class)
                    .setParameter("userId", userId)
                    .setParameter("name", name)
                    .getResultStream()
                    .findFirst();
            if (!accountCheck.isPresent()) {
                AccountModel account = new AccountModel();
                account.setUserId(userId);
                account.setName(name);
                account.setBalance(balance);
                em.persist(account);
                if (account.getId() != 0) {
                    return account;
                } else {
                    throw new DAOException("Insert account failed");
                }
            } else {
                throw new AlreadyExistsException("Account already exists");
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    public boolean delete(int id, int userId) {
        try {
            Optional<AccountModel> typeModel = em.createNamedQuery("Account.findByIdAndUserId", AccountModel.class)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .getResultStream()
                    .findFirst();
            if (typeModel.isPresent()) {
                em.remove(typeModel.get());
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }
}
