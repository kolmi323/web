package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.CategoryTransactionDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class  CategoryTransactionService {
    private CategoryTransactionDAO categoryTransactionDAO;

    public CategoryTransactionService(CategoryTransactionDAO categoryTransactionDAO) {
        this.categoryTransactionDAO = categoryTransactionDAO;
    }

    public HashMap<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getIncomingTransactions(userId, startDate, endDate);
    }

    public HashMap<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return categoryTransactionDAO.getOutgoingTransactions(userId, startDate, endDate);
    }
}
