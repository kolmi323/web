package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO extends DAO {
    private TypeTransactionDAO typeTransactionDAO;

    public TransactionDAO(DataSource ds, TypeTransactionDAO typeTransactionDAO) {
        super(ds);
        this.typeTransactionDAO = typeTransactionDAO;
    }

    public List<TransactionModel> getAll(int typeId, int accountId, Date dateAfter, Date dateBefore) {
        List<Integer> transactionId = this.typeTransactionDAO.getTransactionId(typeId);
        if (transactionId.isEmpty()) {
            throw new NullPointerException("No transaction found for type id " + typeId);
        }
        List<TransactionModel> transactions = new ArrayList<>();
        try (PreparedStatement psst = getDataSource().getConnection()
                .prepareStatement("SELECT * FROM transaction where id = ? " +
                        "AND (from_account_id = ? OR to_account_id = ?) AND date > ? AND date < ?")) {
            for (int id : transactionId) {
                psst.setInt(1, id);
                psst.setInt(2, accountId);
                psst.setInt(3, accountId);
                psst.setDate(4, dateAfter);
                psst.setDate(5, dateBefore);
                ResultSet rs = psst.executeQuery();
                rs.next();
                transactions.add(new TransactionModel(rs.getInt(1), rs.getInt(2),
                        rs.getInt(3), rs.getBigDecimal(4), rs.getDate(5)));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return transactions;
    }
}
