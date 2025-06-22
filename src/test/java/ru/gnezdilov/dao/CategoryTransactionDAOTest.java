package ru.gnezdilov.dao;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;

import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CategoryTransactionDAOTest extends AbstractDAOTest<CategoryTransactionDAO> {
    public CategoryTransactionDAOTest() {
        setPropertyForConnectH2();
        context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        subj = context.getBean(CategoryTransactionDAO.class);
    }

    @Test
    public void getIncomingTransactions_returnInfoTransaction_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> categories = new HashMap<>();
        categories.put("no type", new BigDecimal("600.00"));
        categories.put("hobby", new BigDecimal("500.00"));

        assertEquals(categories, subj.getIncomingTransactions(1, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }

    @Test
    public void getIncomingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> categories = new HashMap<>();

        assertEquals(categories, subj.getIncomingTransactions(2, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }

    @Test
    public void getOutgoingTransactions_returnInfoTransaction_whenCalledWithValidArguments() {
        Map<String, BigDecimal> categories = new HashMap<>();
        categories.put("no type", new BigDecimal("600.00"));
        categories.put("hobby", new BigDecimal("500.00"));

        assertEquals(categories, subj.getIncomingTransactions(1, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }

    @Test
    public void getOutgoingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        Map<String, BigDecimal> categories = new HashMap<>();
        assertEquals(categories, subj.getIncomingTransactions(2, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }

    @Test
    public void insert_successAndReturnCategoryTransactionModel_whenCalledWithValidArguments() {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel(2, 1, 2);
        subj.getEm().getTransaction().begin();
        assertEquals(categoryTransactionModel, subj.insert(1, 2, subj.getEm()));
    }

    @Test (expected = DAOException.class)
    public void insert_failedAndThrowDAOException_whenCalledWithInvalidArgumentsTransactionNotExists() {
        subj.getEm().getTransaction().begin();
        CategoryTransactionModel categoryTransactionModel = subj.insert(1, 3, subj.getEm());
    }

    @Test (expected = DAOException.class)
    public void insert_failedAndThrowDAOException_whenCalledWithInvalidArgumentsTypeNotExists() {
        subj.getEm().getTransaction().begin();
        CategoryTransactionModel categoryTransactionModel = subj.insert(2, 2, subj.getEm());
    }
}
