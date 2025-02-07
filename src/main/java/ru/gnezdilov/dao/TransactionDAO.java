package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends DAO {
    private static final String SQL_STATE_ABSENCE_FOREIGN_KEY = "23503";

    private final String SQL_GET_ALL_TRANSACTIONS = "SELECT t.id, t.from_account_id, t.to_account_id, t.amount, t.date\n" +
            "FROM users AS u\n" +
            "JOIN account AS a ON u.id = a.user_id\n" +
            "JOIN transaction AS t ON a.id = t.from_account_id OR a.id = t.to_account_id\n" +
            "WHERE u.id = ?";

    public TransactionDAO(DataSource ds) {
        super(ds);
    }

    private ResultSet transferMoney(int fromAccountId, int toAccountId, BigDecimal amount) {
        try (Connection con = getDataSource().getConnection()){
            con.setAutoCommit(false);
            PreparedStatement psst = con.prepareStatement("UPDATE account SET balance = balance - ? WHERE id = ?");
            psst.setBigDecimal(1, amount);
            psst.setInt(2, fromAccountId);
            psst.executeUpdate();
            psst = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE id = ?");
            psst.setBigDecimal(1, amount);
            psst.setInt(2, toAccountId);
            psst.executeUpdate();
            psst = con.prepareStatement("INSERT INTO transaction (from_account_id, to_account_id, amount, date) " +
                    "VALUES (?, ?, ?, CURRENT_DATE)", Statement.RETURN_GENERATED_KEYS);
            psst.setInt(1, fromAccountId);
            psst.setInt(2, toAccountId);
            psst.setBigDecimal(3, amount);
            psst.executeUpdate();
            con.commit();
            return psst.getGeneratedKeys();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<TransactionModel> getAll(int userId) {
        List<TransactionModel> transactions = new ArrayList<>();
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement(SQL_GET_ALL_TRANSACTIONS)) {
            psst.setInt(1, userId);
            ResultSet rs = psst.executeQuery();
            while (rs.next()) {
                TransactionModel transaction = new TransactionModel(rs.getInt("id"),
                        rs.getInt("from_account_id"), rs.getInt("to_account_id"),
                        rs.getBigDecimal("amount"), LocalDate.parse(rs.getDate("date").toString()));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return transactions;
    }

    public int insert(int fromAccountId, int toAccountId, BigDecimal amount) {
        try {
            ResultSet rs = transferMoney(fromAccountId, toAccountId, amount);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new DAOException("Insert transaction failed");
            }
        } catch (SQLException e) {
            if (SQL_STATE_ABSENCE_FOREIGN_KEY.equals(e.getSQLState())) {
                throw new DAOException("Account with id " + toAccountId +" not found");
            } else {
                throw new DAOException(e);
            }
        }
    }
}
