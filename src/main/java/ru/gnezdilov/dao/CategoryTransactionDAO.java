package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;
import ru.gnezdilov.dao.exception.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CategoryTransactionDAO extends DAO {
    private final String SQL_INCOMING_TRANSACTION = "select coalesce(ty.name, 'no type'), sum(tr.amount)\n" +
            "from users as us\n" +
            "        join account as ac on us.id = ac.user_id\n" +
            "        join transaction as tr on ac.id = tr.to_account_id\n" +
            "        left join type_transaction as tt on tr.id = tt.transaction_id\n" +
            "        left join type as ty on tt.type_id = ty.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ?\n" +
            "group by ty.name";
    private final String SQL_OUTGOING_TRANSACTION = "select coalesce(ty.name, 'no type'), sum(tr.amount)\n" +
            "from users as us\n" +
            "        join account as ac on us.id = ac.user_id\n" +
            "        join transaction as tr on ac.id = tr.from_account_id\n" +
            "        left join type_transaction as tt on tr.id = tt.transaction_id\n" +
            "        left join type as ty on tt.type_id = ty.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ?\n" +
            "group by ty.name";

    public CategoryTransactionDAO(DataSource dataSource, EntityManagerFactory emf) {
        super(dataSource, emf);
        this.em.setFlushMode(FlushModeType.COMMIT);
    }

    public CategoryTransactionModel insert(int typeId, int transactionId, EntityManager em) {
        try {
            CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
            categoryTransactionModel.setTypeId(typeId);
            categoryTransactionModel.setTransactionId(transactionId);
            em.persist(categoryTransactionModel);
            return categoryTransactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllInTimeFrame(userId, startDate, endDate, "Report.getOutgoingTransaction");
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllInTimeFrame(userId, startDate, endDate, "Report.getIncomingTransaction");
    }

    protected Map<String, BigDecimal> getAllInTimeFrame(int userId, LocalDate startDate, LocalDate endDate, String nameQuery) {
        try {
            Map<String, BigDecimal> result = new HashMap<>();
            List<Object[]> storageReport = em.createNamedQuery(nameQuery)
                    .setParameter("id", userId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
            for (Object[] row : storageReport) {
                result.put((String) row[0], (BigDecimal) row[1]);
            }
            return result;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }

    }
}
