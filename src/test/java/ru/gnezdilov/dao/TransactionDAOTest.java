package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class TransactionDAOTest extends AbstractDAOTest<TransactionDAO> {
    public TransactionDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getTransactionDao();
    }

    @Test
    public void insert_successInsertAndReturnIdNewTransaction_whenCalledWithValidArguments() {
        TransactionModel transaction = new TransactionModel(3, 1, 2,
                new BigDecimal("600.00"), LocalDate.now());
        assertEquals(transaction, subj.insert(1, 1, 2, new BigDecimal("600.00")));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDaoExceptionWithAbsenceForeignKey_whenCalledWithInvalidArgumentToAccountId() {
        TransactionModel transactionModel = subj.insert(1, 1, 3, new BigDecimal("600.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDaoExceptionWithAbsenceForeignKey_whenCalledWithInvalidArgumentFromAccountId() {
        TransactionModel transactionModel = subj.insert(1, 3, 2, new BigDecimal("600.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDAOException_WhenCalledWithInvalidArgumentsAndAccountNotExists() {
        TransactionModel transactionModel = subj.insert(1, 1, 3, new BigDecimal("600.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDAOException_WhenCalledWithInvalidArgumentsAndBalanceOnSenderAccountIsSmall() {
        TransactionModel transactionModel = subj.insert(1, 1, 2, new BigDecimal("6000.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDAOException_WhenCalledWithInvalidArgumentUserId() {
        TransactionModel transactionModel = subj.insert(2, 1, 2, new BigDecimal("600.00"));
    }
}