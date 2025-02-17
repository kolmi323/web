package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.math.BigDecimal;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final AccountService accountService;
    private final TypeService typeService;
    private final ConverterTransactionModelToTransactionDTO converter;

    public TransactionService(TransactionDAO transactionDAO, AccountService accountService, TypeService typeService,
                              ConverterTransactionModelToTransactionDTO converter) {
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.typeService = typeService;
        this.converter = converter;
    }

    public TransactionDTO create(int typeId, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        validateType(userId, typeId);
        validateAccounts(userId, fromAccountId, toAccountId, amount);
        return converter.convert(transactionDAO.insert(typeId, userId, fromAccountId, toAccountId, amount));
    }

    private void validateType(int userId, int typeId) {
        TypeDTO typeDTO = typeService.getById(typeId, userId);
    }

    private void validateAccounts (int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Sender account and receiver accounts cannot be the same");
        } else if (fromAccountId == 0 || toAccountId == 0) {
            throw new IllegalArgumentException("Sender account id and receiver accounts id cannot be the zero");
        }
        assertHasEnoughAccounts(userId, fromAccountId, amount);
        assertReceiverAccount(userId, toAccountId);
    }

    private void assertHasEnoughAccounts (int userId, int fromAccountId, BigDecimal amount) {
        AccountDTO accountDTO = accountService.getById(fromAccountId, userId);
        if (amount.compareTo(accountDTO.getBalance()) > 0) {
            throw new InsufficientFundsException("On Sender account " + fromAccountId + " has insufficient funds");
        }
    }

    private void assertReceiverAccount(int userId, int toAccountId) {
        AccountDTO accountDTO = accountService.getById(toAccountId, userId);
    }
}
