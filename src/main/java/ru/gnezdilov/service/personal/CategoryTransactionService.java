package ru.gnezdilov.service.personal;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.CategoryTransactionDAO;
import ru.gnezdilov.service.converter.ConverterCategoryTransactionModelToCategoryTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class CategoryTransactionService {
    private final CategoryTransactionDAO categoryTransactionDAO;
    private final ConverterCategoryTransactionModelToCategoryTransactionDTO converter;

    public CategoryTransactionService(CategoryTransactionDAO categoryTransactionDAO,
                                      ConverterCategoryTransactionModelToCategoryTransactionDTO converter) {
        this.categoryTransactionDAO = categoryTransactionDAO;
        this.converter = converter;
    }

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getIncomingTransactions(userId, startDate, endDate);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getOutgoingTransactions(userId, startDate, endDate);
    }
}
