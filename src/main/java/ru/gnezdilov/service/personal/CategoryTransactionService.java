package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.transaction.TransactionFilter;
import ru.gnezdilov.dao.transaction.TransactionRepository;

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
        TransactionFilter filter = new TransactionFilter(userId, startDate, endDate, true);
        List<Object[]> transaction = transactionRepository.getIncomingTransaction(filter);
        return handleResult(transaction);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        TransactionFilter filter = new TransactionFilter(userId, startDate, endDate, false);
        List<Object[]> transaction = transactionRepository.getOutgoingTransaction(filter);
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
