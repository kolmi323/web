package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class TransactionDAO extends DAO {
    private static final String SQL_STATE_ABSENCE_FOREIGN_KEY = "23503";

    public TransactionDAO(DataSource ds) {
        super(ds);
    }

    public TransactionModel insert(int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try (Connection con = getDataSource().getConnection()) {
            con.setAutoCommit(false);
            updateFromAccount(userId, fromAccountId, amount, con);
            updateToAccount(toAccountId, amount, con);
            ResultSet rs = createTransaction(userId, fromAccountId, toAccountId, amount, con);
            if(rs.next()) {
                con.commit();
                return new TransactionModel(rs.getInt("id"), fromAccountId, toAccountId, amount,
                        LocalDate.now());
            } else {
                con.rollback();
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

    private void updateFromAccount(int userId, int fromAccountId, BigDecimal amount, Connection con) {
        try {
            PreparedStatement psst = con.prepareStatement("UPDATE account SET balance = balance - ? " +
                    "WHERE id = ? AND user_id = ? AND balance >= ?");
            psst.setBigDecimal(1, amount);
            psst.setInt(2, fromAccountId);
            psst.setInt(3, userId);
            psst.setBigDecimal(4, amount);
            if (psst.executeUpdate() == 0) {
                con.rollback();
                throw new DAOException("Insert transaction failed");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void updateToAccount(int toAccountId, BigDecimal amount, Connection con) {
        try {
            PreparedStatement psst = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE id = ?");
            psst.setBigDecimal(1, amount);
            psst.setInt(2, toAccountId);
            if (psst.executeUpdate() == 0) {
                con.rollback();
                throw new DAOException("Insert transaction failed");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private ResultSet createTransaction(int userId, int fromAccountId, int toAccountId, BigDecimal amount,
                                        Connection con) {
        try {
            PreparedStatement psst = con.prepareStatement("INSERT INTO transaction (from_account_id, to_account_id, amount, date) " +
                    "VALUES (?, ?, ?, CURRENT_DATE)", Statement.RETURN_GENERATED_KEYS);
            psst.setInt(1, fromAccountId);
            psst.setInt(2, toAccountId);
            psst.setBigDecimal(3, amount);
            psst.executeUpdate();
            return psst.getGeneratedKeys();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
