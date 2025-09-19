package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.TransactionRepository;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TypeService typeService;
    private final UserService userService;
    private final ConverterTransactionModelToTransactionDTO converter;

    @Transactional
    public TransactionDTO create(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        validateUser(userId);
        validateType(userId, typeIds);
        validateAccounts(userId, fromAccountId, toAccountId, amount);
        return insert(typeIds, userId, fromAccountId, toAccountId, amount);
    }

    public TransactionDTO insert(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        accountService.updateFromAccount(userId, fromAccountId, amount);
        accountService.updateToAccount(userId, toAccountId, amount);
        TransactionModel transactionModel = createTransaction(typeIds, fromAccountId, toAccountId, amount);
        return converter.convert(transactionModel);
    }

    private void validateUser(int userId) {
        if (!userService.existsById(userId)) {
            throw new NotFoundException("User not found");
        }
    }

    private void validateType(int userId, List<Integer> typeIds) {
        for (int id : typeIds) {
            if (!typeService.existsById(id, userId)) {
                throw new NotFoundException("Type " + id + " not found");
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
        if (!accountService.existsByIdAndUserId(fromAccountId, userId)) {
            throw new NotFoundException("Account " + fromAccountId + " not found");
        }
        if (amount.compareTo(accountService.getByIdAndUserId(fromAccountId, userId).getBalance()) > 0) {
            throw new InsufficientFundsException("On Sender account " + fromAccountId + " has insufficient funds");
        }
    }

    private void assertReceiverAccount(int userId, int toAccountId) {
        if (!accountService.existsByIdAndUserId(toAccountId, userId)) {
            throw new NotFoundException("Account " + toAccountId + " not found");
        }
    }

    private TransactionModel createTransaction(List<Integer> typeIds, int fromAccountId, int toAccountId, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setSenderAccountId(fromAccountId);
        transactionModel.setReceiverAccountId(toAccountId);
        transactionModel.setAmount(amount);
        transactionModel.setDate(LocalDate.now());
        linkingTypeAndTransaction(typeIds, transactionModel);
        transactionModel = transactionRepository.save(transactionModel);
        return transactionModel;
    }

    private void linkingTypeAndTransaction(List<Integer> typeIds, TransactionModel transactionModel) {
        typeIds.forEach(id -> transactionModel.addType(typeService.getModelById(id)));
    }
}
