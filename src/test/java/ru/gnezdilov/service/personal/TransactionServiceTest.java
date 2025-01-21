package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks TransactionService subj;

    @Mock TransactionDAO transactionDAO;
    @Mock ConverterTransactionModelToTransactionDTO converter;

    @Test
    public void getAll_ReturnListTransaction() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.10"), new Date(System.currentTimeMillis()));
        transactionModelList.add(transactionModel);
        when(transactionDAO.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"))).thenReturn(transactionModelList);

        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.10"), new Date(System.currentTimeMillis()));

        when(converter.convert(transactionModel)).thenReturn(transactionDTO);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO);
        assertEquals(transactionDTOList, subj.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10")));

        verify(transactionDAO, times(1)).getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"));
        verify(converter, times(1)).convert(transactionModel);
    }

    @Test
    public void getAll_ReturnEmptyList() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        when(transactionDAO.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"))).thenReturn(transactionModelList);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<TransactionDTO> type = subj.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"));
        assertEquals(transactionDTOList, type);

        verify(transactionDAO, times(1)).getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"));
        verifyNoInteractions(converter);
    }

    @Test
    public void getAll_TransactionDAOThrowDAOException() {
        when(transactionDAO.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10")));

        verify(transactionDAO, times(1)).getAll(1, 2, Date.valueOf("2025-01-10"),
                Date.valueOf("2025-02-10"));
        verifyNoInteractions(converter);
    }
}