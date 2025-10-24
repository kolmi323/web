package ru.gnezdilov.dao.transaction;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;

@RequiredArgsConstructor
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
    private final EntityManager em;

    @Override
    public Map<String, BigDecimal> getMapIncomingReport(TransactionFilter transactionFilter) {
        return getTransactionInformation(transactionFilter, true);
    }

    @Override
    public Map<String, BigDecimal> getMapOutgoingReport(TransactionFilter transactionFilter) {
        return getTransactionInformation(transactionFilter, false);
    }

    private Map<String, BigDecimal> getTransactionInformation(TransactionFilter transactionFilter, boolean isIncoming) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", transactionFilter.getUserId());
        params.put("dateAfter", transactionFilter.getDateAfter());
        params.put("dateBefore", transactionFilter.getDateBefore());

        String query = returnQuery(isIncoming);

        TypedQuery<Object[]> typedQuery = em.createQuery(query, Object[].class);
        params.entrySet()
                .forEach(p -> typedQuery.setParameter(p.getKey(), p.getValue()));

        return handleResult(typedQuery.getResultList());
    }

    private String returnQuery(boolean isIncoming) {
        String query = "SELECT COALESCE(ty.name, 'no type'), SUM(tr.amount) " +
                        "FROM UserModel us " +
                        "JOIN AccountModel ac ON us.id = ac.userId ";
        if (isIncoming) {
            query += "JOIN TransactionModel tr ON ac.id = tr.receiverAccountId ";
        } else {
            query += "JOIN TransactionModel tr ON ac.id = tr.senderAccountId ";
        }
        query += "LEFT JOIN tr.types ty " +
                "WHERE us.id = :userId AND tr.date > :dateAfter AND tr.date < :dateBefore " +
                "GROUP BY ty.name";
        return query;
    }

    private Map<String, BigDecimal> handleResult(List<Object[]> result) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        for (Object[] row : result) {
            transactions.put(String.valueOf(row[0]), (BigDecimal) row[1]);
        }
        return transactions;
    }
}
