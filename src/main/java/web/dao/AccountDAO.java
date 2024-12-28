package web.dao;

import web.CustomException.AccountDataException;
import web.CustomException.DAOException;
import web.dao.AbstractClass.DAO;
import web.dao.Model.AccountModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends DAO {
    public AccountDAO(DataSource ds) {
        super(ds);
    }

    public List<AccountModel> getListAccount(int userId) {
        List<AccountModel> accounts = new ArrayList<>();
        try (PreparedStatement psst = getDataSource().getConnection()
                .prepareStatement("SELECT * FROM account WHERE user_id = ?")) {
            psst.setInt(1, userId);
            psst.executeQuery();
            ResultSet rs = psst.getResultSet();
            while (rs.next()) {
                accounts.add(new AccountModel(rs.getInt("id"), rs.getInt("user_id"),
                         rs.getString("name"), rs.getBigDecimal("balance")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public AccountModel insertAccount(int userId, String name, BigDecimal balance) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("INSERT INTO account (user_id, name, balance) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
        {
            psst.setInt(1, userId);
            psst.setString(2, name);
            psst.setBigDecimal(3, balance);
            psst.executeUpdate();
            ResultSet rs = psst.getGeneratedKeys();
            if (rs.next()) {
                return new AccountModel(rs.getInt(1), userId, name, balance);
            } else {
                throw new DAOException("Insert account failed");
            }
        }
        catch (SQLException e) {
            throw new AccountDataException(e.getMessage(), e);
        }
    }

    //Сочетание name и user_id уникально для account. Почему я не могу удалять по этим параметрам?
    public boolean deleteAccount(int userId, String name) {
        try (PreparedStatement psst = getDataSource().getConnection().prepareStatement
                ("DELETE FROM account WHERE user_id = ? and name = ?"))
        {
            psst.setInt(1, userId);
            psst.setString(2, name);
            return psst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new AccountDataException(e.getMessage(), e);
        }
    }
}
