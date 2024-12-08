package s03.dao;

import s03.dao.AbstractClass.DAO;
import s03.dao.Model.AccountModel;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetterDAO extends DAO {
    public GetterDAO(DataSource ds) {
        super(ds);
    }

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

    public List<AccountModel> getListAccount(int userId) {
        List<AccountModel> accounts = new ArrayList<>();
        try (Statement st = getDataSource().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM account WHERE user_id = " + userId);
            while (rs.next()) {
                accounts.add(new AccountModel(rs.getString("name"), rs.getBigDecimal("balance")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
}
