package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.dao.model.TypeModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

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

    public TransactionModel insert(int typeId, int userId, int fromAccountId, int toAccountId, BigDecimal amount) {
        try (Connection con = getDataSource().getConnection()) {
            validIncomingArguments(typeId, userId, fromAccountId, toAccountId, amount);
            con.setAutoCommit(false);
            try {
                updateFromAccount(userId, fromAccountId, amount, con);
                updateToAccount(userId, toAccountId, amount, con);
                try (ResultSet rs = createTransaction(fromAccountId, toAccountId, amount, con)) {
                    if(rs.next()) {
                        int transactionId = rs.getInt(1);
                        categoryTransactionDAO.insert(typeId, transactionId, con);
                        con.commit();
                        return new TransactionModel(transactionId, fromAccountId, toAccountId, amount, LocalDate.now());
                    } else {
                        throw new DAOException("Insert transaction failed");
                    }
                }
            } catch (DAOException e) {
                con.rollback();
                throw new DAOException(e);
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

    private void validIncomingArguments(int typeId, int userId, int senderAccountId, int receiverAccountId,
                                        BigDecimal amount) {
        assertUserId(userId);
        assertTypeId(userId, typeId);
        assertSenderAccount(userId, senderAccountId, amount);
        assertReceiverAccount(userId, receiverAccountId);
    }

    private void assertSenderAccount(int userId, int senderAccountId, BigDecimal amount) {
        AccountModel accountModel = accountDAO.findById(senderAccountId, userId);
        if (amount.compareTo(accountModel.getBalance()) > 0) {
            throw new InsufficientFundsException("On Sender account " + senderAccountId + " has insufficient funds");
        }
    }

    private void assertReceiverAccount(int userId, int receiverAccountId) {
        AccountModel accountModel = accountDAO.findById(receiverAccountId, userId);
    }

    private void assertTypeId(int userId, int typeId) {
        TypeModel typeModel = typeDAO.findById(userId, typeId);
    }

    private void assertUserId(int userId) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            psst.setInt(1, userId);
            psst.executeQuery();
            ResultSet rs = psst.getResultSet();
            if (!rs.next()) {
                throw new NotFoundException("User with id " + userId + " not found");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
