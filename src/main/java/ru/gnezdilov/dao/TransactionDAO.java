package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.dao.model.TransactionModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransactionDAO extends DAO {
    private final CategoryTransactionDAO categoryTransactionDAO;
    private final AccountDAO accountDAO;
    private final TypeDAO typeDAO;

    public TransactionDAO(DataSource ds, CategoryTransactionDAO categoryTransactionDAO, AccountDAO accountDAO,
                          TypeDAO typeDAO) {
        super(ds);
        this.categoryTransactionDAO = categoryTransactionDAO;
        this.accountDAO = accountDAO;
        this.typeDAO = typeDAO;
    }

    public TransactionModel insert(List<Integer> typeIds, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try (Connection con = getDataSource().getConnection()) {
            con.setAutoCommit(false);
            try {
                updateFromAccount(userId, fromAccountId, amount, con);
                updateToAccount(userId, toAccountId, amount, con);
                try (ResultSet rs = createTransaction(fromAccountId, toAccountId, amount, con)) {
                    if(rs.next()) {
                        int transactionId = rs.getInt(1);
                        linkingTypeAndTransaction(typeIds, transactionId, con);
                        con.commit();
                        return new TransactionModel(transactionId, fromAccountId, toAccountId, amount, LocalDate.now());
                    } else {
                        throw new DAOException("Insert transaction failed");
                    }
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
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
                throw new DAOException("Insert transaction failed");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void updateToAccount(int userId, int toAccountId, BigDecimal amount, Connection con) {
        try {
            PreparedStatement psst = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE id = ? " +
                    "AND user_id = ?");
            psst.setBigDecimal(1, amount);
            psst.setInt(2, toAccountId);
            psst.setInt(3, userId);
            if (psst.executeUpdate() == 0) {
                throw new DAOException("Insert transaction failed");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private ResultSet createTransaction(int fromAccountId, int toAccountId, BigDecimal amount, Connection con) {
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

    private void linkingTypeAndTransaction(List<Integer> typeIds, int transactionId, Connection con) {
        Set<Integer> ids = new HashSet<>();
        for (Integer id : typeIds) {
            if (ids.add(id)) {
                categoryTransactionDAO.insert(id, transactionId, con);
            }
        }
    }
}
