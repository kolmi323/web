package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.CategoryTransactionModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CategoryTransactionDAO extends DAO {
    private final String SQL_INCOMING_TRANSACTION = "select ty.name, sum(tr.amount)\n" +
            "from users as us\n" +
            "         join type as ty on us.id = ty.user_id\n" +
            "         join type_transaction as tt on ty.id = tt.type_id\n" +
            "         join transaction as tr on tt.transaction_id = tr.id\n" +
            "         join account as ac on tr.to_account_id = ac.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ? " +
            "group by ty.name";
    private final String SQL_OUTGOING_TRANSACTION = "select ty.name, sum(tr.amount)\n" +
            "from users as us\n" +
            "         join type as ty on us.id = ty.user_id\n" +
            "         join type_transaction as tt on ty.id = tt.type_id\n" +
            "         join transaction as tr on tt.transaction_id = tr.id\n" +
            "         join account as ac on tr.from_account_id = ac.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ? " +
            "group by ty.name";

    public CategoryTransactionDAO(DataSource dataSource) {
        super(dataSource);
    }

    public CategoryTransactionModel insert(int typeId, int transactionId, Connection con) {
        try (PreparedStatement psst = con.prepareStatement("INSERT INTO type_transaction " +
                "(type_id, transaction_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            psst.setInt(1, typeId);
            psst.setInt(2, transactionId);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new CategoryTransactionModel(rs.getInt(1), typeId, transactionId);
            } else {
                throw new DAOException("Failed to insert category transaction");
            }
        } catch (SQLException e) {
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
        try(PreparedStatement psst = getDataSource().getConnection()
                .prepareStatement(sqlCode)) {
            psst.setInt(1, userId);
            psst.setObject(2, startDate);
            psst.setObject(3, endDate);
            psst.executeQuery();
            ResultSet rs = psst.getResultSet();
            while (rs.next()) {
                transactions.put(rs.getString(1), rs.getBigDecimal(2));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return transactions;
    }
}
