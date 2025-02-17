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
}