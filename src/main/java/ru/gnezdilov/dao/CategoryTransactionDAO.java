package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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

    private HashMap<String, BigDecimal> getAll(int userId, LocalDate startDate, LocalDate endDate,
                                               String sqlCode) {
        HashMap<String, BigDecimal> transactions = new HashMap<>();
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

    public HashMap<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId, startDate, endDate, SQL_INCOMING_TRANSACTION);
    }

    public HashMap<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getAll(userId, startDate, endDate, SQL_OUTGOING_TRANSACTION);
    }
}
