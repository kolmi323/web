package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.CategoryTransactionRepository;
import ru.gnezdilov.service.converter.ConverterCategoryTransactionModelToCategoryTransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryTransactionService {
    private final CategoryTransactionRepository repository;

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return handleResult(repository.getIncomingTransaction(userId, startDate, endDate));
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return handleResult(repository.getOutgoingTransaction(userId, startDate, endDate));
    }

    private Map<String, BigDecimal> handleResult(List<Object[]> result) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        for (Object[] row : result) {
            transactions.put(String.valueOf(row[0]), (BigDecimal) row[1]);
        }
        return transactions;
    }
}
