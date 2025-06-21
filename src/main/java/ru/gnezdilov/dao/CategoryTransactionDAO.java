package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
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

    @Transactional()
    public CategoryTransactionModel insert(int typeId, int transactionId, EntityManager mainEM) {
        try {
            CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
            categoryTransactionModel.setTypeId(typeId);
            categoryTransactionModel.setTransactionId(transactionId);
            mainEM.persist(categoryTransactionModel);
            mainEM.flush();
            return categoryTransactionModel;
        } catch (PersistenceException e) {
            throw new DAOException(e);
        }
    }

    public Map<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllInTimeFrame(userId, startDate, endDate, SQL_INCOMING_TRANSACTION);
    }

    public Map<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllInTimeFrame(userId, startDate, endDate, SQL_OUTGOING_TRANSACTION);
    }

    private Map<String, BigDecimal> getAllInTimeFrame(int userId, LocalDate startDate, LocalDate endDate, String sqlCode) {
        Map<String, BigDecimal> transactions = new HashMap<>();
        try(Connection con = getDataSource().getConnection();
            PreparedStatement psst = con.prepareStatement(sqlCode)) {
            psst.setInt(1, userId);
            psst.setObject(2, startDate);
            psst.setObject(3, endDate);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet() ) {
                while (rs.next()) {
                    transactions.put(rs.getString(1), rs.getBigDecimal(2));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return transactions;
    }
}
