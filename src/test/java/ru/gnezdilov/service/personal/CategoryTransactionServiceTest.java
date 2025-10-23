package ru.gnezdilov.service.personal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gnezdilov.dao.transaction.TransactionFilter;
import ru.gnezdilov.dao.transaction.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTransactionServiceTest {
    @InjectMocks private CategoryTransactionService subj;

    @Mock private TransactionRepository repository;

    @Test
    public void getIncomingTransactions_returnHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        Object[] report = {"hobby", new BigDecimal(1)};
        result.add(report);
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                true);

        when(repository.getIncomingTransaction(transactionFilter)).thenReturn(result);

        assertEquals(hm, subj.getIncomingTransactions(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")));

        verify(repository, times(1)).getIncomingTransaction(transactionFilter);
    }

    @Test
    public void getIncomingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                true);

        when(repository.getIncomingTransaction(transactionFilter)).thenReturn(result);

        assertTrue(subj.getIncomingTransactions(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")).isEmpty());

        verify(repository, times(1)).getIncomingTransaction(transactionFilter);
    }

    @Test
    public void getIncomingTransactions_throwIllegalArgumentException_whenCalledWithInvalidArguments() {
        TransactionFilter transactionFilter = new TransactionFilter(1,
                null,
                null,
                true);

        when(repository.getIncomingTransaction(transactionFilter)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.getIncomingTransactions(1, null, null));

        verify(repository, times(1)).getIncomingTransaction(transactionFilter);
    }

    @Test
    public void getOutgoingTransactions_returnHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        Object[] report = {"hobby", new BigDecimal(1)};
        result.add(report);
        HashMap<String, BigDecimal> hm = new HashMap<>();
        hm.put("hobby", new BigDecimal(1));
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                false);

        when(repository.getOutgoingTransaction(transactionFilter)).thenReturn(result);

        assertEquals(hm, subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")));

        verify(repository, times(1)).getOutgoingTransaction(transactionFilter);
    }

    @Test
    public void getOutgoingTransactions_returnEmptyHashMap_whenCalledWithValidArguments() {
        ArrayList<Object[]> result = new ArrayList<>();
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                false);

        when(repository.getOutgoingTransaction(transactionFilter)).thenReturn(result);

        assertTrue(subj.getOutgoingTransactions(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")).isEmpty());

        verify(repository, times(1)).getOutgoingTransaction(transactionFilter);
    }

    @Test
    public void getOutgoingTransactions_throwIllegalArgumentException_whenCalledWithInvalidArguments() {
        TransactionFilter transactionFilter = new TransactionFilter(1,
                null,
                null,
                false);

        lenient().when(repository.getOutgoingTransaction(transactionFilter)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.getOutgoingTransactions(1, null, null));

        verify(repository, times(1)).getOutgoingTransaction(transactionFilter);
    }
}