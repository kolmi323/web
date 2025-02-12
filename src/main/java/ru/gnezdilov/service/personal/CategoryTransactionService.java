package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.CategoryTransactionDAO;
import ru.gnezdilov.dao.model.CategoryTransactionModel;
import ru.gnezdilov.service.converter.ConverterCategoryTransactionModelToCategoryTransactionDTO;
import ru.gnezdilov.service.dto.CategoryTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class CategoryTransactionService {
    private final CategoryTransactionDAO categoryTransactionDAO;
    private final ConverterCategoryTransactionModelToCategoryTransactionDTO converter;

    public CategoryTransactionService(CategoryTransactionDAO categoryTransactionDAO,
                                      ConverterCategoryTransactionModelToCategoryTransactionDTO converter) {
        this.categoryTransactionDAO = categoryTransactionDAO;
        this.converter = converter;
    }

    public CategoryTransactionDTO create(int typeId, int transactionId) {
        return converter.convert(categoryTransactionDAO.insert(typeId, transactionId));
    }

    public HashMap<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getIncomingTransactions(userId, startDate, endDate);
    }

    public HashMap<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getOutgoingTransactions(userId, startDate, endDate);
    }
}
