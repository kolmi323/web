package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
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
    public void insert_successAndReturnTransactionModel_whenCalledWithValidArguments() {
        TransactionModel transactionModel = new TransactionModel(3, 1, 2, new BigDecimal("500.00"),
                LocalDate.now());
        assertEquals(transactionModel, subj.insert(1,1, 1, 2, new BigDecimal("500.00")));
    }

    @Test (expected = NotFoundException.class)
    public void insert_acceptNotFoundReceiverAccount_whenCalledWithInvalidArgumentToAccountId() {
        TransactionModel transactionModel = subj.insert(1, 1, 1, 3, new BigDecimal("600.00"));
    }

    @Test (expected = NotFoundException.class)
    public void insert_acceptNotFoundSenderAccount_whenCalledWithInvalidArgumentFromAccountId() {
        TransactionModel transactionModel = subj.insert(1,1, 3, 2, new BigDecimal("600.00"));
    }

    @Test (expected = NotFoundException.class)
    public void insert_acceptNotFoundType_WhenCalledWithInvalidArgumentsTypeId() {
        TransactionModel transactionModel = subj.insert(2, 1, 1, 2, new BigDecimal("600.00"));
    }

    @Test (expected = InsufficientFundsException.class)
    public void insert_acceptInsufficientFunds_WhenCalledWithInvalidArgumentsAndBalanceOnSenderAccountIsSmall() {
        TransactionModel transactionModel = subj.insert(1, 1, 1, 2, new BigDecimal("6000.00"));
    }

    @Test (expected = NotFoundException.class)
    public void insert_acceptNotFoundUser_WhenCalledWithInvalidArgumentUserId() {
        TransactionModel transactionModel = subj.insert(1, 3, 1, 2, new BigDecimal("600.00"));
    }
}