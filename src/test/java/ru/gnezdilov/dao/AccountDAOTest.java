package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.AccountModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
public class AccountDAOTest extends AbstractDAOTest<AccountDAO> {
    public AccountDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getAccountDao();
    }

    @Test
    public void existsById_returnTrue_whenCalledWithValidArguments() {
        assertTrue(subj.existsById(1, 1));
    }

    @Test
    public void existsById_returnFalse_whenCalledWithInvalidArguments() {
        assertFalse(subj.existsById(1, 3));
    }

    @Test
    public void findById_returnAccount_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(1, 1, "sber", new BigDecimal("1000.00"));

        assertEquals(accountModel, subj.findById(1, 1));
    }

    @Test (expected = NotFoundException.class)
    public void findById_acceptNotFound_whenCalledWithInvalidArguments() {
        subj.findById(1, 3);
    }

    @Test
    public void getAll_returnListAccountModel_whenCalledWithValidArguments() {
        List<AccountModel> accountModels = new ArrayList<>();
        accountModels.add(new AccountModel(1, 1, "sber", new BigDecimal("1000.00")));
        accountModels.add(new AccountModel(2, 1, "T", new BigDecimal("500.00")));

        assertEquals(accountModels, subj.getAll(1));
    }

    @Test
    public void getAll_returnEmptyList_whenCalledWithInvalidArguments() {
        List<AccountModel> accountModels = new ArrayList<>();

        assertEquals(accountModels, subj.getAll(2));
    }

    @Test
    public void insert_successInsertAndReturnAccountModel_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(3, 1, "vtb", new BigDecimal("2000.00"));

        assertEquals(accountModel, subj.insert(1, "vtb", new BigDecimal("2000.00")));
    }

    @Test (expected = AlreadyExistsException.class)
    public void insert_failedInsertAndThrowAlreadyExistsException_whenCalledWithInvalidArguments() {
        subj.insert(1, "sber", new BigDecimal("2000.00"));
    }

    @Test
    public void delete_successDelete_whenCalledWithValidArguments() {
        assertTrue(subj.delete(1, 1));
    }

    @Test
    public void delete_failedDelete_whenCalledWithInvalidArguments() {
        assertFalse(subj.delete(3, 1));
    }
}*/
