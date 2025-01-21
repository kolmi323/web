package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    private TransactionDAO transactionDAO;
    private ConverterTransactionModelToTransactionDTO converter;

    public TransactionService(TransactionDAO transactionDAO, ConverterTransactionModelToTransactionDTO converter) {
        this.transactionDAO = transactionDAO;
        this.converter = converter;
    }

    public List<TransactionDTO> getAll(int typeId, int accountId, Date dateAfter, Date dateBefore) {
        return transactionDAO.getAll(typeId, accountId, dateAfter, dateBefore).stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
