package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final AccountService accountService;
    private final TypeService typeService;
    private final UserService userService;
    private final ConverterTransactionModelToTransactionDTO converter;

    public TransactionService(TransactionDAO transactionDAO, AccountService accountService, TypeService typeService,
                              UserService userService, ConverterTransactionModelToTransactionDTO converter) {
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.typeService = typeService;
        this.userService = userService;
        this.converter = converter;
    }

    public TransactionDTO create(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        validateUser(userId);
        validateType(userId, typeIds);
        validateAccounts(userId, fromAccountId, toAccountId, amount);
        return converter.convert(transactionDAO.insert(typeIds, userId, fromAccountId, toAccountId, amount));
    }

    private void validateUser(int userId) {
        if (!userService.existsById(userId)) {
            throw new NotFoundException("User not found");
        }
    }

    private void validateType(int userId, List<Integer> typeIds) {
        if (typeIds.isEmpty()) {
            throw new IllegalArgumentException("Type IDs cannot be empty");
        }
        Set<Integer> ids = new HashSet<>();
        for (int id : typeIds) {
            if (!typeService.existsById(id, userId)) {
                throw new NotFoundException("Type " + id + " not found");
            }
            if (!ids.add(id)) {
                throw new IllegalArgumentException("Type " + id + " repeated");
            }
        }
    }

    private void validateAccounts (int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        if (fromAccountId == 0 && toAccountId == 0) {
            throw new IllegalArgumentException("Sender account id and receiver accounts id can't be the zero");
        } else if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Sender account and receiver accounts can't be the same");
        }
        assertHasEnoughAccounts(userId, fromAccountId, amount);
        assertReceiverAccount(userId, toAccountId);
    }

    private void assertHasEnoughAccounts (int userId, int fromAccountId, BigDecimal amount) {
        if (!accountService.existsById(fromAccountId, userId)) {
            throw new NotFoundException("Account " + fromAccountId + " not found");
        }
        if (amount.compareTo(accountService.getById(fromAccountId, userId).getBalance()) > 0) {
            throw new InsufficientFundsException("On Sender account " + fromAccountId + " has insufficient funds");
        }
    }

    private void assertReceiverAccount(int userId, int toAccountId) {
        if (!accountService.existsById(toAccountId, userId)) {
            throw new NotFoundException("Account " + toAccountId + " not found");
        }
    }
}
