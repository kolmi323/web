package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.dao.model.TransactionModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionDAOTest extends AbstractDAOTest<TransactionDAO> {
    public TransactionDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getTransactionDao();
    }

    @Test
    public void insert_successInsertAndReturnIdNewTransaction_whenCalledWithValidArguments() {
        TransactionModel transaction = new TransactionModel(2, 1, 2,
                new BigDecimal("600.00"), LocalDate.now());
        assertEquals(transaction, subj.insert(1, 1, 2, new BigDecimal("600.00")));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDaoExceptionWithAbsenceForeignKey_whenCalledWithInvalidArguments() {
        TransactionModel transactionModel = subj.insert(1, 1, 3, new BigDecimal("600.00"));
    }
}