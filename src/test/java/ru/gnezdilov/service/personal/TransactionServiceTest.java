package ru.gnezdilov.service.personal;

import org.junit.Assert;
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
import ru.gnezdilov.service.dto.TypeDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService subj;

    @Mock private TransactionDAO transactionDAO;
    @Mock private AccountService accountService;
    @Mock private CategoryTransactionService categoryTransactionService;
    @Mock private TypeService typeService;
    @Mock private ConverterTransactionModelToTransactionDTO converter;

    private final TypeDTO TYPE = new TypeDTO(1, 1, "hobby");
    private final AccountDTO FIRST_ACCOUNT = new AccountDTO(1, 1, "sber", new BigDecimal("1000"));
    private final AccountDTO SECOND_ACCOUNT = new AccountDTO(2, 1, "T", new BigDecimal("3000"));


    @Test
    public void create_successAndReturnTransactionDTO_whenCalledWithValidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1)).thenReturn(SECOND_ACCOUNT);

        TransactionModel transactionModel = new TransactionModel(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        when(transactionDAO.insert(1, 1, 1, 2, new BigDecimal("500.00"))).thenReturn(transactionModel);

        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        when(converter.convert(transactionModel)).thenReturn(transactionDTO);

        assertEquals(transactionDTO, subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verify(transactionDAO, times(1)).insert(1, 1, 1, 2, new BigDecimal("500.00"));
        verify(converter, times(1)).convert(transactionModel);
    }

    @Test
    public void create_acceptNotFoundType_whenCalledWithInvalidArgumentTypeId() {
        when(typeService.getById(1, 1)).thenThrow(new NotFoundException("Type with id " + TYPE.getId() + " not found"));

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Type with id 1 not found", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDAOExceptionOnTypeDao_whenCalledWithValidArguments() {
        when(typeService.getById(1, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptIllegalArgument_whenCalledWithInvalidAccountIdsSame() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subj.create(1, 1, 1, 1, new BigDecimal("500.00")));

        assertEquals("Sender account and receiver accounts cannot be the same", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptIllegalArgument_whenCalledWithInvalidAccountIdEqualZero() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subj.create(1, 1, 1, 0, new BigDecimal("500.00")));

        assertEquals("Sender account id and receiver accounts id cannot be the zero", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundSenderAccount_whenCalledWithInvalidFromAccountId() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1))
                .thenThrow(new NotFoundException("Account with id " + FIRST_ACCOUNT.getId() + " - not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account with id 1 - not found", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDAOExceptionOnSenderAccountDao_whenCalledWithValidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptInsufficientFunds_whenCalledWithInvalidAmount() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenThrow(
                new InsufficientFundsException("On Sender account " + FIRST_ACCOUNT.getId() + " has insufficient funds"));

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> subj.create(1, 1, 1, 2, new BigDecimal("5000.00")));
        assertEquals("On Sender account 1 has insufficient funds", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundReceiverAccount_whenCalledWithInvalidToAccountId() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1))
                .thenThrow(new NotFoundException("Account with id " + SECOND_ACCOUNT.getId() + " - not found"));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account with id 2 - not found", exception.getMessage());

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDaoExceptionOnReceiverAccountDao_whenCalledWithValidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1)).thenThrow(new DAOException(new SQLException()));

        assertThrows(DAOException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verifyNoInteractions(transactionDAO);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDaoExceptionOnTransactionDao_whenCalledWithValidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1)).thenReturn(SECOND_ACCOUNT);

        when(transactionDAO.insert(1, 1, 1, 2, new BigDecimal("500.00"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verify(transactionDAO, times(1)).insert(1, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundOnTransactionDao_whenCalledWithInvalidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1)).thenReturn(SECOND_ACCOUNT);

        when(transactionDAO.insert(1, 1, 1, 2, new BigDecimal("500.00")))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verify(transactionDAO, times(1)).insert(1, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptInsufficientFundsOnTransactionDao_whenCalledWithInvalidArguments() {
        when(typeService.getById(1, 1)).thenReturn(TYPE);
        when(accountService.getById(1, 1)).thenReturn(FIRST_ACCOUNT);
        when(accountService.getById(2, 1)).thenReturn(SECOND_ACCOUNT);

        when(transactionDAO.insert(1, 1, 1, 2, new BigDecimal("500.00")))
                .thenThrow(InsufficientFundsException.class);

        assertThrows(InsufficientFundsException.class, () -> subj.create(1, 1, 1, 2, new BigDecimal("500.00")));

        verify(typeService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(1, 1);
        verify(accountService, times(1)).getById(2, 1);
        verify(transactionDAO, times(1)).insert(1, 1, 1, 2, new BigDecimal("500.00"));
        verifyNoInteractions(converter);
    }
}

