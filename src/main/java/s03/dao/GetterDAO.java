package s03.dao;

import s03.dao.AbstractClass.DAO;
import s03.dao.AbstractClass.Model;
import s03.dao.Model.AccountModel;
import s03.dao.Model.TypeTransactionModel;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<List<AccountModel>> getListAccount(int userId) {
        List<AccountModel> accounts = new ArrayList<>();
        try (Statement st = getDataSource().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM account WHERE user_id = " + userId);
            while (rs.next()) {
                accounts.add(new AccountModel(rs.getString("name"), rs.getBigDecimal("balance")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts);
    }

    public Optional<List<TypeTransactionModel>> getListTypeTransaction(int userId) {
        List<TypeTransactionModel> types = new ArrayList<>();
        try (Statement st = getDataSource().getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM type WHERE user_id = " + userId);
            while (rs.next()) {
                TypeTransactionModel typeTransactionModel = new TypeTransactionModel(rs.getString("name"));
                typeTransactionModel.setUserId(rs.getInt("user_id"));
                types.add(typeTransactionModel);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return types.isEmpty() ? Optional.empty() : Optional.of(types);
    }
}
