package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.CategoryTransactionDAO;
import ru.gnezdilov.dao.exception.DAOException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryTransactionServiceTest {
    @InjectMocks CategoryTransactionService subj;

    @Mock CategoryTransactionDAO categoryTransactionDAO;

    @Test
    public void getIncomingTransactions_returnHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        when(categoryTransactionDAO.getIncomingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"))).thenReturn(hm);

        assertEquals(hm, subj.getIncomingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")));

        verify(categoryTransactionDAO, times(1)).getIncomingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getIncomingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> hm = new HashMap<>();
        when(categoryTransactionDAO.getIncomingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenReturn(hm);

        assertTrue(categoryTransactionDAO.getIncomingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")).isEmpty());

        verify(categoryTransactionDAO, times(1)).getIncomingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getIncomingTransactions_acceptDAOEception_whenCalledWithValidArguments() {
        when(categoryTransactionDAO.getIncomingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getIncomingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30")));

        verify(categoryTransactionDAO, times(1)).getIncomingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getOutgoingTransactions_returnHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        when(categoryTransactionDAO.getOutgoingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"))).thenReturn(hm);

        assertEquals(hm, subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")));

        verify(categoryTransactionDAO, times(1)).getOutgoingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getOutgoingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> hm = new HashMap<>();
        when(categoryTransactionDAO.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenReturn(hm);

        assertTrue(subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")).isEmpty());

        verify(categoryTransactionDAO, times(1)).getOutgoingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getOutgoingTransactions_acceptDAOException_whenCalledWithValidArguments() {
        when(categoryTransactionDAO.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getOutgoingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30")));

        verify(categoryTransactionDAO, times(1)).getOutgoingTransactions(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }
}