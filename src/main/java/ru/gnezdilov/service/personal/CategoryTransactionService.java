package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.TransactionRepository;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.entities.TypeModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryTransactionService {
    private final TransactionRepository transactionRepository;

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> transaction = transactionRepository.getIncomingTransaction(userId, startDate, endDate);
        return handleResult(transaction);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> transaction = transactionRepository.getOutgoingTransaction(userId, startDate, endDate);
        return handleResult(transaction);
    }

    private Map<String, BigDecimal> handleResult(List<Object[]> result) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        for (Object[] row : result) {
            transactions.put(String.valueOf(row[0]), (BigDecimal) row[1]);
        }
        return transactions;
    }
}
