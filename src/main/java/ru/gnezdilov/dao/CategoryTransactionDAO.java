package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryTransactionDAO extends DAO {
    private final String SQL_INCOMING_TRANSACTION = "select ty.name, tr.amount\n" +
            "from users as us\n" +
            "         join type as ty on us.id = ty.user_id\n" +
            "         join type_transaction as tt on ty.id = tt.type_id\n" +
            "         join transaction as tr on tt.transaction_id = tr.id\n" +
            "         join account as ac on tr.to_account_id = ac.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ?";
    private final String SQL_OUTGOING_TRANSACTION = "select ty.name, tr.amount\n" +
            "from users as us\n" +
            "         join type as ty on us.id = ty.user_id\n" +
            "         join type_transaction as tt on ty.id = tt.type_id\n" +
            "         join transaction as tr on tt.transaction_id = tr.id\n" +
            "         join account as ac on tr.from_account_id = ac.id\n" +
            "where us.id = ? and tr.date > ? and tr.date < ?";

    public CategoryTransactionDAO(DataSource dataSource) {
        super(dataSource);
    }

    private HashMap<String, BigDecimal> getTransactions(int userId, LocalDate startDate, LocalDate endDate,
                                                          String sqlCode) {
        HashMap<String, BigDecimal> transactions = new HashMap<>();
        try(PreparedStatement psst = getDataSource().getConnection()
                .prepareStatement(sqlCode)) {
            psst.setInt(1, userId);
            psst.setDate(2, Date.valueOf(startDate));
            psst.setDate(3, Date.valueOf(endDate));
            ResultSet rs = psst.executeQuery();
            while (rs.next()) {
                transactions.put(rs.getString(1), rs.getBigDecimal(2));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return transactions;
    }

    public HashMap<String, BigDecimal> getIncomingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getTransactions(userId, startDate, endDate, SQL_INCOMING_TRANSACTION);
    }

    public HashMap<String, BigDecimal> getOutgoingTransactions(int userId, LocalDate startDate, LocalDate endDate) {
        return getTransactions(userId, startDate, endDate, SQL_OUTGOING_TRANSACTION);
    }
}
