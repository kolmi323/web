package ru.gnezdilov.dao.transaction;

import java.util.List;

public interface TransactionRepositoryCustom {
    List<Object[]> getIncomingTransaction(TransactionFilter transactionFilter);
    List<Object[]> getOutgoingTransaction(TransactionFilter transactionFilter);
}
