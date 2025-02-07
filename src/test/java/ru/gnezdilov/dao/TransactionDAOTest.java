package ru.gnezdilov.dao;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionDAOTest extends AbstractDAOTest<TransactionDAO> {
    public TransactionDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getTransactionDao();
    }


    @Test
    public void getAll() {
    }

    @Test
    public void insert() {
    }
}