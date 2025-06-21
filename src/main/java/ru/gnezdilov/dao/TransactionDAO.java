package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.web.controller.personal.type.TypeUpdateController;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TransactionDAO extends DAO {
    private final CategoryTransactionDAO categoryTransactionDAO;
    private final TypeUpdateController update;

    public TransactionDAO(DataSource ds, EntityManagerFactory emf, CategoryTransactionDAO categoryTransactionDAO, TypeUpdateController update) {
        super(ds, emf);
        this.categoryTransactionDAO = categoryTransactionDAO;
        this.update = update;
        this.em.setFlushMode(FlushModeType.COMMIT);
    }

    @Transactional
    public TransactionModel insert(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                updateFromAccount(userId, fromAccountId, amount);
                updateToAccount(userId, toAccountId, amount);
                TransactionModel transactionModel = createTransaction(fromAccountId, toAccountId, amount);
                linkingTypeAndTransaction(typeIds, transactionModel.getId(), this.em);
                tx.commit();
                return transactionModel;
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    protected void updateFromAccount(int userId, int fromAccountId, BigDecimal amount) throws PersistenceException {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserIdWhereBalanceGreater", AccountModel.class)
                    .setParameter("id", fromAccountId)
                    .setParameter("userId", userId)
                    .setParameter("balance", amount)
                    .getSingleResult();
            accountModel.setBalance(accountModel.getBalance().subtract(amount));
            em.merge(accountModel);
            em.flush();
        } catch (NoResultException e) {
            throw new DAOException("Insert transaction failed");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    protected void updateToAccount(int userId, int toAccountId, BigDecimal amount) throws PersistenceException {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserIdWhereBalanceGreater", AccountModel.class)
                    .setParameter("id", toAccountId)
                    .setParameter("userId", userId)
                    .setParameter("balance", amount)
                    .getSingleResult();
            accountModel.setBalance(accountModel.getBalance().add(amount));
            em.merge(accountModel);
            em.flush();
        } catch (NoResultException e) {
            throw new DAOException("Insert transaction failed");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    @Transactional
    protected TransactionModel createTransaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setSenderAccountId(fromAccountId);
            transactionModel.setReceiverAccountId(toAccountId);
            transactionModel.setAmount(amount);
            transactionModel.setDate(LocalDate.now());
            em.persist(transactionModel);
            em.flush();
            return transactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    private void linkingTypeAndTransaction(List<Integer> typeIds, int transactionId, EntityManager transactionEM) {
        typeIds.stream().collect(Collectors.toSet()).forEach(id -> categoryTransactionDAO.insert(id, transactionId, transactionEM));
    }
}
