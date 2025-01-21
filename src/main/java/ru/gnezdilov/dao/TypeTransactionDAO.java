package ru.gnezdilov.dao;

import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TransactionModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionDAO extends DAO {
    public TypeTransactionDAO(DataSource ds) {
        super(ds);
    }

    public List<Integer> getTransactionId(int typeId) {
        List<Integer> transactionIds = new ArrayList<>();
        try (PreparedStatement psst = getDataSource().getConnection()
                .prepareStatement("SELECT transaction_id FROM type_transaction WHERE type_id = ?")) {
            psst.setInt(1, typeId);
            psst.executeQuery();
            ResultSet rs = psst.getResultSet();
            while (rs.next()) {
                transactionIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return transactionIds;
    }
}
