package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.DAOException;
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
    public void getAll_returnListOfTransactionModel_whenCalledWithValidArguments() {
        TransactionModel transactionModel = new TransactionModel(1, 1, 2, new BigDecimal("500.00"), LocalDate.of(2025, 01, 01));
        List<TransactionModel> transactions = new ArrayList<>();
        transactions.add(transactionModel);
        transactions.add(transactionModel);

        assertEquals(transactions, subj.getAll(1));
    }

    @Test
    public void getAll_returnEmptyList_whenCalledWithValidArguments() {
        List<TransactionModel> transactions = new ArrayList<>();

        assertEquals(transactions, subj.getAll(2));
    }

    @Test
    public void getAll_returnEmptyList_whenCalledWithInvalidArguments() {
        List<TransactionModel> transactions = new ArrayList<>();

        assertEquals(transactions, subj.getAll(3));
    }

    @Test
    public void insert_successInsertAndReturnIdNewTransaction_whenCalledWithValidArguments() {
        assertEquals(2, subj.insert(1, 2, new BigDecimal("600.00")));
    }

    @Test (expected = DAOException.class)
    public void insert_failedInsertAndThrowDaoExceptionWithAbsenceForeignKey_whenCalledWithInvalidArguments() {
        int id = subj.insert(1, 3, new BigDecimal("600.00"));
    }
}