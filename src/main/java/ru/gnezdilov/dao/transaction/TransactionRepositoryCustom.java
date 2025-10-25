package ru.gnezdilov.dao.transaction;

import java.math.BigDecimal;
import java.util.Map;

public interface TransactionRepositoryCustom {
    Map<String, BigDecimal> getMapIncomingReport(TransactionFilter transactionFilter);
    Map<String, BigDecimal> getMapOutgoingReport(TransactionFilter transactionFilter);
}
