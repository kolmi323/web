package ru.gnezdilov.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.dao.entities.AccountModel;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class AccountRepositoryTest {
    @Autowired
    AccountRepository subj;

    @Test
    public void existsByIdAndUserId_returnTrue_whenCalledWithValidArguments() {
        assertTrue(subj.existsByIdAndUserId(1, 1));
    }

    @Test
    public void existsByIdAndUserId_returnFalse_whenCalledWithValidArguments() {
        assertFalse(subj.existsByIdAndUserId(1, 2));
    }

    @Test
    public void findByIdAndUserId_returnAccountModel_whenCalledWithValidArguments() {
        AccountModel accountModel = subj.findByIdAndUserId(1, 1);

        assertEquals(accountModel.getId(), 1);
        assertEquals(accountModel.getName(), "sber");
        assertEquals(accountModel.getBalance(), new BigDecimal("1000.00"));
    }

    @Test
    public void findAllByUserId_returnListAccount_whenCalledWithValidArguments() {
        List<AccountModel> accountModels = subj.findAllByUserId(1);

        assertEquals(accountModels.size(), 2);
        assertEquals(accountModels.get(0).getName(), "sber");
        assertEquals(accountModels.get(1).getName(), "T");
    }

    @Test
    public void deleteByIdAndUserId() {
        assertEquals((Integer) 1, subj.deleteByIdAndUserId(1, 1));
    }

    @Test
    public void findByIdAndUserIdAndBalanceIsGreaterThanEqual() {
        AccountModel accountModel = subj
                .findByIdAndUserIdAndBalanceIsGreaterThanEqual(1, 1, new BigDecimal(600));

        assertEquals(accountModel.getId(), 1);
        assertEquals(accountModel.getName(), "sber");
        assertEquals(accountModel.getBalance(), new BigDecimal("1000.00"));
    }
}