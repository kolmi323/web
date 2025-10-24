package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.transaction.TransactionFilter;
import ru.gnezdilov.dao.transaction.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryTransactionService {
    private final TransactionRepository transactionRepository;

    public Map<String, BigDecimal> getIncomingReport(int userId, LocalDate startDate, LocalDate endDate) {
        TransactionFilter filter = new TransactionFilter(userId, startDate, endDate);
        return transactionRepository.getMapIncomingReport(filter);
    }

    public Map<String, BigDecimal> getOutgoingReport(int userId, LocalDate startDate, LocalDate endDate) {
        TransactionFilter filter = new TransactionFilter(userId, startDate, endDate);
        return transactionRepository.getMapOutgoingReport(filter);
    }
}
