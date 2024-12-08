package s03.dao;

import s03.CustomException.DAOException;
import s03.dao.AbstractClass.DAO;
import s03.dao.Model.AccountModel;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO extends DAO {
    public AccountDAO(DataSource ds) {
        super(ds);
    }

    public boolean insertAccount(int userId, AccountModel accountModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO account (user_id, name, balance) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            psst.setInt(1, userId);
            psst.setString(2, accountModel.getName());
            psst.setBigDecimal(3, accountModel.getBalance());
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
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

    public boolean deletedAccount(int userId, AccountModel accountModel) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("DELETE FROM account WHERE name = ? and user_id = ?"))
        {
            psst.setString(1, accountModel.getName());
            psst.setInt(2, userId);
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
}
