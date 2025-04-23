package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServletServiceTest {
    @InjectMocks private TransactionService subj;

    @Mock private TransactionDAO transactionDAO;
    @Mock private AccountService accountService;
    @Mock private UserService userService;
    @Mock private TypeService typeService;
    @Mock private ConverterTransactionModelToTransactionDTO converter;

    private List<Integer> TYPE_IDS = new ArrayList<>(Arrays.asList(1));
    private final AccountDTO ACCOUNT_DTO = new AccountDTO(1, 1, "sber", new BigDecimal("1000.00"));

    @Test
    public void create_successAndReturnTransactionDTO_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(2, 1)).thenReturn(true);

        TransactionModel transactionModel = new TransactionModel(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        when(transactionDAO.insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"))).thenReturn(transactionModel);

        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        when(converter.convert(transactionModel)).thenReturn(transactionDTO);

        assertEquals(transactionDTO, subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verify(transactionDAO, times(1)).insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"));
        verify(converter, times(1)).convert(transactionModel);
    }

    @Test
    public void create_acceptNotFound_whenCalledWithInvalidArgumentUserId() {
        when(userService.existsById(1)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("User not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verifyNoInteractions(typeService);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDaoExceptionOnUserDao_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verifyNoInteractions(typeService);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundType_whenCalledWithInvalidArgumentTypeId() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenThrow(new NotFoundException("Type with id " + TYPE_IDS.get(0) + " not found"));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Type with id 1 not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDAOExceptionOnTypeDao_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptIllegalArgument_whenCalledWithInvalidAccountIdsSame() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 1, new BigDecimal("500.00")));

        assertEquals("Sender account and receiver accounts can't be the same", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptIllegalArgument_whenCalledWithInvalidAccountIdEqualZero() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subj.create(TYPE_IDS, 1, 0, 0, new BigDecimal("500.00")));

        assertEquals("Sender account id and receiver accounts id can't be the zero", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundSenderAccount_whenCalledWithInvalidFromAccountId() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);

        when(accountService.existsById(1, 1))
                .thenThrow(new NotFoundException("Account with id 1 - not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account with id 1 - not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDAOExceptionOnSenderAccountDao_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(1, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptInsufficientFunds_whenCalledWithInvalidAmount() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(1, 1)).thenThrow(
                new InsufficientFundsException("On Sender account 1 has insufficient funds"));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("5000.00")));
        assertEquals("On Sender account 1 has insufficient funds", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundReceiverAccount_whenCalledWithInvalidToAccountId() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(2, 1))
                .thenThrow(new NotFoundException("Account with id 2 - not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account with id 2 - not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDaoExceptionOnReceiverAccountDao_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(2, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDaoExceptionOnTransactionDao_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(2, 1)).thenReturn(true);

        when(transactionDAO.insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verify(transactionDAO, times(1)).insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundOnTransactionDao_whenCalledWithInvalidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(2, 1)).thenReturn(true);

        when(transactionDAO.insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verify(transactionDAO, times(1)).insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptInsufficientFundsOnTransactionDao_whenCalledWithInvalidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getById(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsById(2, 1)).thenReturn(true);

        when(transactionDAO.insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")))
                .thenThrow(InsufficientFundsException.class);

        assertThrows(InsufficientFundsException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsById(2, 1);
        verify(transactionDAO, times(1)).insert(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }
}

