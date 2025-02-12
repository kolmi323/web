package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final AccountService accountService;
    private final CategoryTransactionService categoryTransactionService;
    private final TypeService typeService;
    private final ConverterTransactionModelToTransactionDTO converter;

    public TransactionService(TransactionDAO transactionDAO, AccountService accountService,
                              CategoryTransactionService categoryTransactionService, TypeService typeService,
                              ConverterTransactionModelToTransactionDTO converter) {
        this.transactionDAO = transactionDAO;
        this.accountService = accountService;
        this.categoryTransactionService = categoryTransactionService;
        this.typeService = typeService;
        this.converter = converter;
    }

    public TransactionDTO create(int userId, int fromAccountId, int toAccountId, BigDecimal amount, int typeId) {
        try {
            List<AccountDTO> accountDTO = accountService.getAll(userId);
            if (accountDTO.isEmpty()) {
                throw new EmptyListException("You have no accounts");
            }
            boolean isIncomingArgumentsValid = isTypeIdValid(userId, typeId) &&
                    isSenderAccountIdValid(accountDTO, fromAccountId) &&
                    isReceiverAccountIdValid(fromAccountId, toAccountId) &&
                    isSenderAmountValid(accountDTO, fromAccountId, amount);
            if (isIncomingArgumentsValid) {
                TransactionModel transactionModel = transactionDAO.insert(userId, fromAccountId, toAccountId, amount);
                categoryTransactionService.create(typeId, transactionModel.getId());
                return converter.convert(transactionModel);
            }
        } catch (EmptyListException | NotFoundException | InsufficientFundsException | IncomingArgumentsException e) {
            throw new ServiceException(e);
        }
        return null;
    }

    private boolean isTypeIdValid(int userId, int typeId) {
        List<TypeDTO> types = typeService.getAll(userId);
        if (types.isEmpty()) {
            throw new EmptyListException("You have no types");
        } else if (types.stream().noneMatch(t -> t.getId() == typeId)) {
            throw new NotFoundException("Type with id " + typeId + " - not found");
        } else {
            return true;
        }
    }

    private boolean isSenderAccountIdValid(List<AccountDTO> accounts, int senderAccountId) {
        if (accounts.stream().anyMatch(a -> a.getId() == senderAccountId)) {
            AccountDTO account = accounts.stream().filter(a -> a.getId() == senderAccountId).findFirst().get();
            if (account.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                throw new InsufficientFundsException("On sender account id " + senderAccountId + " is insufficient funds");
            } else {
                return true;
            }
        } else {
            throw new NotFoundException("Sender transaction with id " + senderAccountId + " - not found");
        }
    }

    private boolean isReceiverAccountIdValid(int senderAccountId, int receiverAccountId) {
        if (senderAccountId == receiverAccountId) {
            throw new IncomingArgumentsException("Id sender account and id receiver must not be the same");
        } else {
            return true;
        }
    }

    private boolean isSenderAmountValid(List<AccountDTO> accounts,int senderAccountId, BigDecimal amount) {
        AccountDTO account = accounts.stream().filter(a -> a.getId() == senderAccountId).findFirst().get();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Sender amount can not be more than sender account balance");
        } else {
            return true;
        }
    }
}
