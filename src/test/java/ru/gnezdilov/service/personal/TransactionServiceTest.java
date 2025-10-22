package ru.gnezdilov.service.personal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gnezdilov.dao.TransactionRepository;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService subj;

    @Mock private AccountService accountService;
    @Mock private UserService userService;
    @Mock private TypeService typeService;
    @Mock private ConverterTransactionModelToTransactionDTO converter;
    @Mock private TransactionRepository repository;

    private List<Integer> TYPE_IDS = new ArrayList<>(Arrays.asList(1));
    private final AccountDTO ACCOUNT_DTO = new AccountDTO(1, 1, "sber", new BigDecimal("1000.00"));

    @Test
    public void create_successAndReturnTransactionDTO_whenCalledWithValidArguments() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsByIdAndUserId(1, 1)).thenReturn(true);
        when(accountService.getByIdAndUserId(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsByIdAndUserId(2, 1)).thenReturn(true);

        TypeModel type = new TypeModel(1, 1, "cool");
        TransactionModel transactionModelRequest = new TransactionModel(0, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        transactionModelRequest.addType(type);
        TransactionModel transactionModelResponse = new TransactionModel(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        transactionModelResponse.addType(type);
        when(typeService.getModelById(1)).thenReturn(type);
        when(repository.save(transactionModelRequest)).thenReturn(transactionModelResponse);

        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, new BigDecimal("500.00"), LocalDate.now());
        when(converter.convert(transactionModelResponse)).thenReturn(transactionDTO);

        TransactionDTO result = subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00"));
        assertEquals(transactionDTO, result);

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(2, 1);
        verify(repository, times(1))
                .save(new TransactionModel(0, 1, 2, new BigDecimal("500.00"), LocalDate.now()));
        verify(converter, times(1)).convert(transactionModelResponse);
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
        verifyNoInteractions(repository);
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
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptIllegalArgumentException_whenCalledWithInvalidAccountIdsSame() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 1, new BigDecimal("500.00")));

        assertEquals("Sender account and receiver accounts can't be the same", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verifyNoInteractions(accountService);
        verifyNoInteractions(repository);
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
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundSenderAccount_whenCalledWithInvalidFromAccountId() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsByIdAndUserId(1, 1)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account 1 not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(1, 1);
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptInsufficientFunds_whenCalledWithInvalidAmount() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.existsByIdAndUserId(1, 1)).thenReturn(true);

        AccountDTO account = new AccountDTO(1, 1, "sber", BigDecimal.ZERO);
        when(accountService.getByIdAndUserId(1, 1)).thenReturn(account);

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class,
                () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("5000.00")));
        assertEquals("On Sender account 1 has insufficient funds", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(1, 1);
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptNotFoundReceiverAccount_whenCalledWithInvalidToAccountId() {
        when(userService.existsById(1)).thenReturn(true);
        when(typeService.existsById(1, 1)).thenReturn(true);
        when(accountService.getByIdAndUserId(1, 1)).thenReturn(ACCOUNT_DTO);
        when(accountService.existsByIdAndUserId(1, 1)).thenReturn(true);
        when(accountService.existsByIdAndUserId(2, 1)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> subj.create(TYPE_IDS, 1, 1, 2, new BigDecimal("500.00")));
        assertEquals("Account 2 not found", exception.getMessage());

        verify(userService, times(1)).existsById(1);
        verify(typeService, times(1)).existsById(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(1, 1);
        verify(accountService, times(1)).existsByIdAndUserId(2, 1);
        verifyNoInteractions(repository);
        verifyNoInteractions(converter);
    }
}

