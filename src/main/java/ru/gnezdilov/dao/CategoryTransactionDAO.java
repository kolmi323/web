package ru.gnezdilov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;
import ru.gnezdilov.dao.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CategoryTransactionDAO {
    private final CategoryTransactionRepository categoryTransactionRepository;

    public CategoryTransactionModel insert(int typeId, int transactionId, EntityManager em) {
        try {
            CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
            categoryTransactionModel.setTypeId(typeId);
            categoryTransactionModel.setTransactionId(transactionId);
            categoryTransactionRepository.save(categoryTransactionModel);
            return categoryTransactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> resultIncoming = categoryTransactionRepository.getIncomingTransaction(userId, startDate, endDate);
        return handleResultAndReturnMapReport(resultIncoming);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> resultOutgoing = categoryTransactionRepository.getOutgoingTransaction(userId, startDate, endDate);
        return handleResultAndReturnMapReport(resultOutgoing);
    }

    private Map<String, BigDecimal> handleResultAndReturnMapReport(List<Object[]> result) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        for (Object[] row : result) {
            transactions.put(String.valueOf(row[0]), (BigDecimal) row[1]);
        }
        return transactions;
    }
}
