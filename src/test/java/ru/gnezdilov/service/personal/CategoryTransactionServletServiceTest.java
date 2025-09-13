package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.CategoryTransactionRepository;
import ru.gnezdilov.service.converter.ConverterCategoryTransactionModelToCategoryTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryTransactionServletServiceTest {
    @InjectMocks private CategoryTransactionService subj;

    @Mock private CategoryTransactionRepository repository;
    @Mock private ConverterCategoryTransactionModelToCategoryTransactionDTO converter;

    @Test
    public void getIncomingTransactions_returnHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        Object[] report = {"hobby", new BigDecimal(1)};
        result.add(report);
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        when(repository.getIncomingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"))).thenReturn(result);

        assertEquals(hm, subj.getIncomingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")));

        verify(repository, times(1)).getIncomingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getIncomingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        when(repository.getIncomingTransaction(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenReturn(result);

        assertTrue(repository.getIncomingTransaction(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")).isEmpty());

        verify(repository, times(1)).getIncomingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getOutgoingTransactions_returnHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        Object[] report = {"hobby", new BigDecimal(1)};
        result.add(report);
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        when(repository.getOutgoingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"))).thenReturn(result);

        assertEquals(hm, subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")));

        verify(repository, times(1)).getOutgoingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }

    @Test
    public void getOutgoingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        when(repository.getOutgoingTransaction(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30"))).thenReturn(result);

        assertTrue(subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-10"),
                LocalDate.parse("2025-01-30")).isEmpty());

        verify(repository, times(1)).getOutgoingTransaction(1,
                LocalDate.parse("2025-01-10"), LocalDate.parse("2025-01-30"));
    }
}