package web.dao;

import web.CustomException.DAOException;
import web.CustomException.UserDataException;
import web.dao.AbstractClass.DAO;
import web.dao.Model.UserModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends DAO {
    public UserDAO(DataSource ds) {
        super(ds);
    }

    // этот метод использовать если insertUser будет плохо выдавать SQLException
    public List<String> getEmails() {
        List<String> emails = new ArrayList<>();
        try (Statement st = getDataSource().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT email FROM users");
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emails;
    }

    public UserModel insertUser(String name, String email, String password) {
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
            throw new UserDataException(e.getMessage(), e);
        }
    }

    public Optional<UserModel> entryUser (String email, String password) {
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
            throw new UserDataException(e.getMessage(), e);
        }
        return Optional.empty();
    }
}
