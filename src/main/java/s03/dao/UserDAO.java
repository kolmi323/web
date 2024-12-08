package s03.dao;

import s03.CustomException.DAOException;
import s03.dao.AbstractClass.DAO;
import s03.dao.Model.UserModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {
    public UserDAO(DataSource ds) {
        super(ds);
    }

    public boolean insertUser(UserModel userModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO users (name, email, password) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            psst.setString(1, userModel.getName());
            psst.setString(2, userModel.getEmail());
            psst.setString(3, userModel.getPassword());
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                userModel.setId(rs.getInt(1));
                return true;
            } else {
                throw new DAOException();
            }
        } catch (DAOException daoe) {
            System.out.println(daoe.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean entryUser (UserModel userModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("SELECT * FROM users WHERE email = ? AND password = ?")) {
            psst.setString(1, userModel.getEmail());
            psst.setString(2, userModel.getPassword());
            ResultSet rs = psst.executeQuery();
            if (rs.next()) {
                userModel.setId(rs.getInt(1));
                userModel.setName(rs.getString("name"));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
