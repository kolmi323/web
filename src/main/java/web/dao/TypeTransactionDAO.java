package web.dao;

import web.CustomException.DAOException;
import web.CustomException.TypeTransactionDataException;
import web.dao.AbstractClass.DAO;
import web.dao.Model.TypeTransactionModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypeTransactionDAO extends DAO {
    public TypeTransactionDAO(DataSource ds) {
        super(ds);
    }

    public TypeTransactionModel updateType(int userId, String oldName, String newName) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("UPDATE type SET name = ? WHERE name = ? AND user_id = ?", Statement.RETURN_GENERATED_KEYS)) {
            psst.setString(1, newName);
            psst.setString(2, oldName);
            psst.setInt(3, userId);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new TypeTransactionModel(rs.getInt(1), userId, newName);
            } else {
                throw new DAOException("Not found type");
            }
        } catch (SQLException e) {
            throw new TypeTransactionDataException(e.getMessage(), e);
        }
    }

    public TypeTransactionModel insertType(int userId, String name) {
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
            throw new TypeTransactionDataException(e.getMessage(), e);
        }
    }

    public boolean deleteType(int userId, String name) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("DELETE FROM type WHERE user_id = ? and name = ?"))
        {
            psst.setInt(1, userId);
            psst.setString(2, name);
            if (psst.executeUpdate() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<TypeTransactionModel> getListTypeTransaction(int userId) {
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
            throw new TypeTransactionDataException(e.getMessage(), e);
        }
        return types;
    }
}
