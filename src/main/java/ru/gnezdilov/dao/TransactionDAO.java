package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.web.controller.personal.type.TypeUpdateController;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TransactionDAO {
    @PersistenceContext
    private EntityManager em;
    private final CategoryTransactionDAO categoryTransactionDAO;
    private final TypeUpdateController update;

    public TransactionDAO(CategoryTransactionDAO categoryTransactionDAO, TypeUpdateController update) {
        this.categoryTransactionDAO = categoryTransactionDAO;
        this.update = update;
    }

    @Transactional
    public TransactionModel insert(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            try {
                updateFromAccount(userId, fromAccountId, amount);
                updateToAccount(userId, toAccountId, amount);
                TransactionModel transactionModel = createTransaction(fromAccountId, toAccountId, amount);
                linkingTypeAndTransaction(typeIds, transactionModel.getId(), this.em);
                return transactionModel;
            } catch (Exception e) {
                throw e;
            }
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    protected void updateFromAccount(int userId, int fromAccountId, BigDecimal amount) throws PersistenceException {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserIdWhereBalanceGreater", AccountModel.class)
                    .setParameter("id", fromAccountId)
                    .setParameter("userId", userId)
                    .setParameter("balance", amount)
                    .getSingleResult();
            accountModel.setBalance(accountModel.getBalance().subtract(amount));
            em.merge(accountModel);
        } catch (NoResultException e) {
            throw new DAOException("Insert transaction failed");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    protected void updateToAccount(int userId, int toAccountId, BigDecimal amount) throws PersistenceException {
        try {
            AccountModel accountModel = em.createNamedQuery("Account.findByIdAndUserIdWhereBalanceGreater", AccountModel.class)
                    .setParameter("id", toAccountId)
                    .setParameter("userId", userId)
                    .setParameter("balance", amount)
                    .getSingleResult();
            accountModel.setBalance(accountModel.getBalance().add(amount));
            em.merge(accountModel);
        } catch (NoResultException e) {
            throw new DAOException("Insert transaction failed");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    protected TransactionModel createTransaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setSenderAccountId(fromAccountId);
            transactionModel.setReceiverAccountId(toAccountId);
            transactionModel.setAmount(amount);
            transactionModel.setDate(LocalDate.now());
            em.persist(transactionModel);
            return transactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    private void linkingTypeAndTransaction(List<Integer> typeIds, int transactionId, EntityManager transactionEM) {
        typeIds.stream().collect(Collectors.toSet()).forEach(id -> categoryTransactionDAO.insert(id, transactionId, transactionEM));
    }
}
