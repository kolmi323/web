package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.TypeModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TypeDAO extends DAO {
    private static final String UNIQUE_CONSTRAINT_VIOLATION = "23505";

    public TypeDAO(DataSource ds) {
        super(ds);
    }

    public TypeModel update(int id, int userId, String newName) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("UPDATE type SET name = ? WHERE id = ? AND user_id = ?",
                     Statement.RETURN_GENERATED_KEYS)) {
            psst.setString(1, newName);
            psst.setInt(2, id);
            psst.setInt(3, userId);
            psst.executeUpdate();
            try (ResultSet rs = psst.getGeneratedKeys()) {
                if (rs.next()) {
                    return new TypeModel(rs.getInt(1), userId, newName);
                } else {
                    throw new DAOException("Not found type");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public TypeModel insert(int userId, String name) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("INSERT INTO type (user_id, name) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            psst.setInt(1, userId);
            psst.setString(2, name);
            psst.executeUpdate();
            try (ResultSet rs = psst.getGeneratedKeys()) {
                if (rs.next()) {
                    return new TypeModel(rs.getInt(1), userId, name);
                } else {
                    throw new DAOException("Insert Type Error");
                }
            }
        } catch (SQLException e) {
            if (UNIQUE_CONSTRAINT_VIOLATION.equals(e.getSQLState())) {
                throw new AlreadyExistsException("Type already exists");
            } else {
                throw new DAOException(e);
            }
        }
    }

    public boolean delete(int id, int userId) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("DELETE FROM type_transaction WHERE type_id = ?;\n" +
                        "DELETE FROM type WHERE id = ? and user_id = ?")) {
            psst.setInt(1, id);
            psst.setInt(2, id);
            psst.setInt(3, userId);
            return psst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<TypeModel> getAll(int userId) {
        List<TypeModel> types = new ArrayList<>();
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("SELECT * FROM type WHERE user_id = ?")) {
            psst.setInt(1, userId);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                while (rs.next()) {
                    types.add(new TypeModel
                            (rs.getInt("id"), rs.getInt("user_id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return types;
    }

    public boolean existsById(int userId, int id) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("SELECT EXISTS(SELECT FROM type WHERE id = ? AND user_id = ?)")) {
            psst.setInt(1, id);
            psst.setInt(2, userId);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
