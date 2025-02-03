package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private final TransactionDAO transactionDAO;
    private final ConverterTransactionModelToTransactionDTO converter;


    public TransactionService(TransactionDAO transactionDAO, ConverterTransactionModelToTransactionDTO converter) {
        this.transactionDAO = transactionDAO;
        this.converter = converter;
    }

    public List<TransactionDTO> getAll(int userId) {
        return this.transactionDAO.getAll(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public int create(int fromAccountId, int toAccountId, BigDecimal amount) {
        return this.transactionDAO.insert(fromAccountId, toAccountId, amount);
    }
}
