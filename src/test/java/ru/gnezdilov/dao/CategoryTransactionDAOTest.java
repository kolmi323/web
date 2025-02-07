package ru.gnezdilov.dao;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CategoryTransactionDAOTest extends AbstractDAOTest<CategoryTransactionDAO> {
    public CategoryTransactionDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getCategoryTransactionDao();
    }

    @Test
    public void getIncomingTransactions_returnInfoTransaction_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> categories = new HashMap<>();
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
        HashMap<String, BigDecimal> categories = new HashMap<>();
        categories.put("hobby", new BigDecimal("500.00"));

        assertEquals(categories, subj.getIncomingTransactions(1, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }

    @Test
    public void getOutgoingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> categories = new HashMap<>();

        assertEquals(categories, subj.getIncomingTransactions(2, LocalDate.of(2025, 01, 01),
                LocalDate.of(2025, 01, 030)));
    }
}