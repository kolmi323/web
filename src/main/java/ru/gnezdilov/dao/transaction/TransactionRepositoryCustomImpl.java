package ru.gnezdilov.dao.transaction;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@RequiredArgsConstructor
public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<Object[]> getIncomingTransaction(TransactionFilter transactionFilter) {
        return getTransaction(transactionFilter);
    }

    @Override
    public List<Object[]> getOutgoingTransaction(TransactionFilter transactionFilter) {
        return getTransaction(transactionFilter);
    }

    private List<Object[]> getTransaction(TransactionFilter transactionFilter) {
        Map<String, Object> params = new HashMap<>();
        String query = returnQuery(transactionFilter.isIncoming());
        params.put("userId", transactionFilter.getUserId());
        params.put("dateAfter", transactionFilter.getDateAfter());
        params.put("dateBefore", transactionFilter.getDateBefore());

        TypedQuery<Object[]> typedQuery = em.createQuery(query, Object[].class);
        params.entrySet()
                .forEach(p -> typedQuery.setParameter(p.getKey(), p.getValue()));

        return typedQuery.getResultList();
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
}
