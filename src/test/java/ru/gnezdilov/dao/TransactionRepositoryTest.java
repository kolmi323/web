package ru.gnezdilov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import ru.gnezdilov.WebApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = WebApplication.class)
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository subj;

    private List<Object[]> transactions;

    @BeforeEach
    public void setUp() throws Exception {
        transactions = new ArrayList<>();
        Object[] firstElement = {"no type", new BigDecimal("600.00")};
        Object[] secondElement = {"hobby", new BigDecimal("500.00")};
        transactions.add(firstElement);
        transactions.add(secondElement);
    }

    @Test
    public void getIncomingTransaction_returnListObject_whenCalledWithValidArgument() {
        List<Object[]> incomingTransactions = subj.getIncomingTransaction(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));
        assertNotNull(incomingTransactions);
        assertEquals(transactions.get(0), incomingTransactions.get(0));
        assertEquals(transactions.get(1), incomingTransactions.get(1));
    }

    @Test
    public void getOutgoingTransaction_returnListObject_whenCalledWithValidArgument() {
        List<Object[]> incomingTransactions = subj.getOutgoingTransaction(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));
        assertNotNull(incomingTransactions);
        assertEquals(transactions.get(0), incomingTransactions.get(0));
        assertEquals(transactions.get(1), incomingTransactions.get(1));
    }
}