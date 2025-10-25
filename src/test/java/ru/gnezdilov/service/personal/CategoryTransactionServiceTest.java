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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTransactionServiceTest {
    @InjectMocks private CategoryTransactionService subj;

    @Mock private TransactionRepository repository;

    @Test
    public void getIncomingReport_returnHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> result = new HashMap<>();
        result.put("hobby", new BigDecimal(1));
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        when(repository.getMapIncomingReport(transactionFilter)).thenReturn(result);

        assertEquals(result, subj.getIncomingReport(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")));

        verify(repository, times(1)).getMapIncomingReport(transactionFilter);
    }

    @Test
    public void getIncomingReport_returnEmptyHashMap_whenCalledWithValidArguments() {
        Map<String, BigDecimal> result = new HashMap<>();
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        when(repository.getMapIncomingReport(transactionFilter)).thenReturn(result);

        assertTrue(subj.getIncomingReport(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")).isEmpty());

        verify(repository, times(1)).getMapIncomingReport(transactionFilter);
    }

    @Test
    public void getIncomingReport_throwIllegalArgumentException_whenCalledWithInvalidArguments() {
        TransactionFilter transactionFilter = new TransactionFilter(1,
                null,
                null);

        when(repository.getMapIncomingReport(transactionFilter)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.getIncomingReport(1, null, null));

        verify(repository, times(1)).getMapIncomingReport(transactionFilter);
    }

    @Test
    public void getOutgoingReport_returnHashMap_whenCalledWithValidArguments() {
        HashMap<String, BigDecimal> result = new HashMap<>();
        result.put("hobby", new BigDecimal(1));
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        when(repository.getMapOutgoingReport(transactionFilter)).thenReturn(result);

        assertEquals(result, subj.getOutgoingReport(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")));

        verify(repository, times(1)).getMapOutgoingReport(transactionFilter);
    }

    @Test
    public void getOutgoingReport_returnEmptyHashMap_whenCalledWithValidArguments() {
        Map<String, BigDecimal> result = new HashMap<>();
        TransactionFilter transactionFilter = new TransactionFilter(1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        when(repository.getMapOutgoingReport(transactionFilter)).thenReturn(result);

        assertTrue(subj.getOutgoingReport(1, LocalDate.parse("2025-01-01"),
                LocalDate.parse("2025-12-31")).isEmpty());

        verify(repository, times(1)).getMapOutgoingReport(transactionFilter);
    }

    @Test
    public void getOutgoingReport_throwIllegalArgumentException_whenCalledWithInvalidArguments() {
        TransactionFilter transactionFilter = new TransactionFilter(1,
                null,
                null);

        lenient().when(repository.getMapOutgoingReport(transactionFilter)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.getOutgoingReport(1, null, null));

        verify(repository, times(1)).getMapOutgoingReport(transactionFilter);
    }
}