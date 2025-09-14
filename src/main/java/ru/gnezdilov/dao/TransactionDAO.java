package ru.gnezdilov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.exception.DAOException;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class    TransactionDAO {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryTransactionRepository categoryTransactionRepository;

    @Transactional
    public TransactionModel insert(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            try {
                updateFromAccount(userId, fromAccountId, amount);
                updateToAccount(userId, toAccountId, amount);
                TransactionModel transactionModel = createTransaction(fromAccountId, toAccountId, amount);
                linkingTypeAndTransaction(typeIds, transactionModel.getId());
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
            AccountModel accountModel = accountRepository.findByIdAndUserIdAndBalanceIsGreaterThanEqual(fromAccountId, userId, amount);
            accountModel.setBalance(accountModel.getBalance().subtract(amount));
            accountRepository.save(accountModel);
        } catch (NoResultException e) {
            throw new DAOException("Insert transaction failed");
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    protected void updateToAccount(int userId, int toAccountId, BigDecimal amount) throws PersistenceException {
        try {
            AccountModel accountModel = accountRepository.findByIdAndUserId(toAccountId, userId);
            accountModel.setBalance(accountModel.getBalance().add(amount));
            accountRepository.save(accountModel);
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
            transactionRepository.save(transactionModel);
            return transactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    private void linkingTypeAndTransaction(List<Integer> typeIds, int transactionId) {
        typeIds.stream().collect(Collectors.toSet()).forEach(id -> {
            CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
            categoryTransactionModel.setTypeId(id);
            categoryTransactionModel.setTransactionId(transactionId);
            categoryTransactionRepository.save(categoryTransactionModel);
        });
    }
}
