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
        List<TransactionModel> transaction = transactionRepository.getIncomingTransaction(userId, startDate, endDate);
        return handleResult(transaction);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        List<TransactionModel> transaction = transactionRepository.getOutgoingTransaction(userId, startDate, endDate);
        return handleResult(transaction);
    }

    private Map<String, BigDecimal> handleResult(List<TransactionModel> transactions) {
        Map<String, BigDecimal> report = new HashMap<>();
        transactions.forEach(transaction -> handleTransaction(transaction, report));
        return report;
    }

    private void handleTransaction(TransactionModel transaction, Map<String, BigDecimal> report) {
        if (transaction.getTypes().isEmpty()) {
            if (report.containsKey("no type")) {
                report.replace("no type", report.get("no type").add(transaction.getAmount()));
            } else {
                report.put("no type", BigDecimal.ZERO);
            }
        } else {
            transaction.getTypes().forEach(type -> handleType(transaction, type, report));
        }
    }

    private void handleType(TransactionModel transaction, TypeModel type, Map<String, BigDecimal> report) {
        if (report.containsKey(type.getName())) {
            report.replace(type.getName(), report.get(type.getName()).add(transaction.getAmount()));
        } else {
            report.put(type.getName(), transaction.getAmount());
        }
    }
}
