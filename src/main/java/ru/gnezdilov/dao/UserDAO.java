package ru.gnezdilov.dao;

import ru.gnezdilov.dao.customexception.DAOException;
import ru.gnezdilov.dao.abstractclass.DAO;
import ru.gnezdilov.dao.model.UserModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO extends DAO {
    public UserDAO(DataSource ds) {
        super(ds);
    }

    public UserModel insert(String name, String email, String password) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            psst.setString(1, name);
            psst.setString(2, email);
            psst.setString(3, password);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new UserModel(rs.getInt(1), name, email, password);
            } else {
                throw new DAOException("Insert user failed");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public Optional<UserModel> entry(String email, String password) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("SELECT * FROM users WHERE email = ? AND password = ?")) {
            psst.setString(1, email);
            psst.setString(2, password);
            ResultSet rs = psst.executeQuery();
            if (rs.next()) {
                return Optional.of(new UserModel(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("password")));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
