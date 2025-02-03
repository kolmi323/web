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
import java.sql.SQLException;
import java.time.LocalDate;
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
    public void getAll_returnListTransactionDTO_whenCalledWithValidArguments() {
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.00"), LocalDate.now());
        List<TransactionModel> transactionModelList = new ArrayList<>();
        transactionModelList.add(transactionModel);
        when(transactionDAO.getAll(1)).thenReturn(transactionModelList);

        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.00"), LocalDate.now());
        when(converter.convert(transactionModel)).thenReturn(transactionDTO);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO);
        assertEquals(transactionDTOList, subj.getAll(1));

        verify(transactionDAO, times(1)).getAll(1);
        verify(converter, times(1)).convert(transactionModel);
    }

    @Test
    public void getAll_returnEmptyList_whenCalledWithValidArguments() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        when(transactionDAO.getAll(1)).thenReturn(transactionModelList);

        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        assertEquals(transactionDTOList, subj.getAll(1));

        verify(transactionDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getAll_shouldAcceptDAOException_whenCalledWithValidArguments() {
        when(transactionDAO.getAll(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1));

        verify(transactionDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_returnIdNewTransaction_whenCalledWithValidArguments() {
        when(transactionDAO.insert(1,2, new BigDecimal("1000.00"))).thenReturn(1);

        assertEquals(1, subj.create(1,2,new BigDecimal("1000.00")));

        verify(transactionDAO, times(1)).insert(1,2,new BigDecimal("1000.00"));
    }

    @Test
    public void create_returnDAOExceptionWithMessageInsertFailed_whenCalledWithValidArguments() {
        DAOException daoException = new DAOException("Insert transaction failed");
        when(transactionDAO.insert(1,2, new BigDecimal("1000.00"))).thenThrow(daoException);

        DAOException exception = assertThrows(DAOException.class,
                () ->subj.create(1, 2, new BigDecimal("1000.00")));
        assertEquals(daoException, exception);

        verify(transactionDAO, times(1))
                .insert(1,2,new BigDecimal("1000.00"));
    }

    @Test
    public void create_returnDAOExceptionWithMessageAccountNotFound_whenCalledWithValidArguments() {
        int toAccountId = 3;
        DAOException daoException = new DAOException("Account with id " + toAccountId + " not found");
        when(transactionDAO.insert(1,toAccountId, new BigDecimal("1000.00"))).thenThrow(daoException);

        DAOException exception = assertThrows(DAOException.class, () ->subj
                .create(1, toAccountId, new BigDecimal("1000.00")));
        assertEquals(daoException, exception);

        verify(transactionDAO, times(1)).
                insert(1,toAccountId,new BigDecimal("1000.00"));
    }

    @Test
    public void create_returnDAOExceptionWithSQLException_whenCalledWithValidArguments() {
        DAOException daoException = new DAOException(new SQLException());
        when(transactionDAO.insert(1,2, new BigDecimal("1000.00"))).thenThrow(daoException);

        DAOException exception = assertThrows(DAOException.class, () ->subj
                .create(1,2,new BigDecimal("1000.00")));
        assertEquals(daoException, exception);

        verify(transactionDAO, times(1)).insert(1,2,new BigDecimal("1000.00"));

    }
}