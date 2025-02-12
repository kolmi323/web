package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.ServiceException;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.CategoryTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService subj;

    @Mock private TransactionDAO transactionDAO;
    @Mock private AccountService accountService;
    @Mock private CategoryTransactionService categoryTransactionService;
    @Mock private TypeService typeService;
    @Mock private ConverterTransactionModelToTransactionDTO converter;

    @Test
    public void create_successCretedTransactionAndReturnTransactionDTO_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        TransactionModel transactionModel = new TransactionModel(1, 1, 2, new BigDecimal("1000.00"), LocalDate.now());
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, new BigDecimal("1000.00"), LocalDate.now());
        when(converter.convert(transactionModel)).thenReturn(transactionDTO);

        when(transactionDAO.insert(1, 1, 2, new BigDecimal("1000.00"))).thenReturn(transactionModel);

        CategoryTransactionDTO categoryTransactionDTO = new CategoryTransactionDTO(1, 1, 1);
        when(categoryTransactionService.create(1, 1)).thenReturn(categoryTransactionDTO);

        assertEquals(transactionDTO, subj.create(1, 1, 2, new BigDecimal("1000.00"), 1));

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verify(categoryTransactionService, times(1)).create(1, 1);
        verify(converter, times(1)).convert(transactionModel);
        verify(transactionDAO, times(1)).insert(1, 1, 2, new BigDecimal("1000.00"));
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithEmptyListAccountException_whenCalledWithValidArguments() {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 2, new BigDecimal("1000.00"), 1));
        assertEquals("You have no accounts", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verifyNoInteractions(typeService);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithEmptyListTypes_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        List<TypeDTO> typeDTOList = new ArrayList<>();
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 2, new BigDecimal("1000.00"), 1));
        assertEquals("You have no types", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithNotFoundExceptionType_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 2, new BigDecimal("1000.00"), 2));
        assertEquals("Type with id 2 - not found", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithNotFoundSenderAccount_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 2, 3, new BigDecimal("1000.00"), 1));
        assertEquals("Sender transaction with id 2 - not found", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithInsufficientFundsOnSenderAccount_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("0"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 2, new BigDecimal("1000.00"), 1));
        assertEquals("On sender account id 1 is insufficient funds", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithIncomingArguments_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 1, new BigDecimal("1000.00"), 1));
        assertEquals("Id sender account and id receiver must not be the same", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowServiceExceptionWithInsufficientFundsAmount_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> subj.create(1, 1, 2, new BigDecimal("2000.00"), 1));
        assertEquals("Sender amount can not be more than sender account balance", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verifyNoInteractions(transactionDAO);
    }

    @Test
    public void create_failedAndThrowDaoExceptionWithMessageFailed_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        when(transactionDAO.insert(1, 1, 2, new BigDecimal("1000.00")))
                .thenThrow(new DAOException("Insert transaction failed"));
        DAOException exception = assertThrows(DAOException.class,() -> subj
                .create(1, 1, 2, new BigDecimal("1000.00"), 1));

        assertEquals("Insert transaction failed", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verify(transactionDAO, times(1)).insert(1, 1, 2, new BigDecimal("1000.00"));
    }

    @Test
    public void create_failedAndThrowDaoExceptionWithMessageNotFoundId_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        when(transactionDAO.insert(1, 1, 2, new BigDecimal("1000.00")))
                .thenThrow(new DAOException("Account with id 1 not found"));
        DAOException exception = assertThrows(DAOException.class,() -> subj
                .create(1, 1, 2, new BigDecimal("1000.00"), 1));

        assertEquals("Account with id 1 not found", exception.getMessage());

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verify(transactionDAO, times(1)).insert(1, 1, 2, new BigDecimal("1000.00"));
    }

    @Test
    public void create_failedAndThrowDaoExceptionWithSQLException_whenCalledWithValidArguments() {
        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "work");
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeDTOList.add(typeDTO);
        when(typeService.getAll(1)).thenReturn(typeDTOList);

        when(transactionDAO.insert(1, 1, 2, new BigDecimal("1000.00")))
                .thenThrow(new DAOException(new SQLException()));
        assertThrows(DAOException.class,() -> subj
                .create(1, 1, 2, new BigDecimal("1000.00"), 1));

        verify(accountService, times(1)).getAll(1);
        verify(typeService, times(1)).getAll(1);
        verifyNoInteractions(categoryTransactionService);
        verifyNoInteractions(converter);
        verify(transactionDAO, times(1)).insert(1, 1, 2, new BigDecimal("1000.00"));
    }
}

