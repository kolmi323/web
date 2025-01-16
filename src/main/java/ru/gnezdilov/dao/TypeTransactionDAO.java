package ru.gnezdilov.dao;

import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.model.TypeTransactionModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionDAO extends DAO {
    private static final String UNIQUE_CONSTRAINT_VIOLATION = "23505";

    public TypeTransactionDAO(DataSource ds) {
        super(ds);
    }

    public TypeTransactionModel update(int id, int userId, String newName) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("UPDATE type SET name = ? WHERE id = ? AND user_id = ?", Statement.RETURN_GENERATED_KEYS)) {
            psst.setString(1, newName);
            psst.setInt(2, id);
            psst.setInt(3, userId);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new TypeTransactionModel(rs.getInt(1), userId, newName);
            } else {
                throw new DAOException("Not found type");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public TypeTransactionModel insert(int userId, String name) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO type (user_id, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            psst.setInt(1, userId);
            psst.setString(2, name);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new TypeTransactionModel(rs.getInt(1), userId, name);
            } else {
                throw new DAOException("Insert Type Error");
            }
        } catch (SQLException e) {
            if (UNIQUE_CONSTRAINT_VIOLATION.equals(e.getSQLState())) {
                throw new AlreadyExistsException("Type already exists");
            } else {
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

    public boolean delete(int id, int userId) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("DELETE FROM type WHERE id = ? and user_id = ?"))
        {
            psst.setInt(1, id);
            psst.setInt(2, userId);
            return psst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public List<TypeTransactionModel> getAll(int userId) {
        List<TypeTransactionModel> types = new ArrayList<>();
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("SELECT * FROM type WHERE user_id = ?")) {
            psst.setInt(1, userId);
            psst.executeQuery();
            ResultSet rs = psst.getResultSet();
            while (rs.next()) {
                types.add(new TypeTransactionModel
                        (rs.getInt("id"), rs.getInt("user_id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return types;
    }
}
