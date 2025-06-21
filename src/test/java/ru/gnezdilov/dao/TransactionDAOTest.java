package ru.gnezdilov.dao;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.TransactionModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TransactionDAOTest extends AbstractDAOTest<TransactionDAO> {
    private List<Integer> LIST_TYPES_ID = new ArrayList<Integer>(Arrays.asList(1));

    public TransactionDAOTest() {
        setPropertyForConnectH2();
        context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        subj = context.getBean(TransactionDAO.class);
    }

    @Test
    public void insert_successAndReturnTransactionModel_whenCalledWithValidArguments() {
        TransactionModel transactionModel = new TransactionModel(3, 1, 2, new BigDecimal("500.00"),
                LocalDate.now());
        assertEquals(transactionModel, subj.insert(LIST_TYPES_ID,1, 1, 2, new BigDecimal("500.00")));
    }

    @Test (expected = DAOException.class)
    public void insert_acceptDaoException_whenCalledWithInvalidArgumentTypeId() {
        try {
            LIST_TYPES_ID.add(3);
            subj.insert(LIST_TYPES_ID, 1, 1, 2, new BigDecimal("500.00"));
        } finally {
            LIST_TYPES_ID.remove(1);
        }
    }

    @Test (expected = DAOException.class)
    public void insert_acceptDaoException_whenCalledWithInvalidArgumentUserId() {
        subj.insert(LIST_TYPES_ID, 2, 1, 2, new BigDecimal("500.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_acceptDaoException_whenCalledWithInvalidArgumentFromAccountId() {
        subj.insert(LIST_TYPES_ID, 1, 3, 2, new BigDecimal("500.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_acceptDaoException_whenCalledWithInvalidArgumentToAccountId() {
        subj.insert(LIST_TYPES_ID, 1, 1, 3, new BigDecimal("500.00"));
    }

    @Test (expected = DAOException.class)
    public void insert_acceptDaoException_whenCalledWithInvalidArgumentAmount() {
        subj.insert(LIST_TYPES_ID, 1, 1, 2, new BigDecimal("5000.00"));
    }
}
