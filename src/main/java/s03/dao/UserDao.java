package s03.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import s03.CustomException.DAOException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final DataSource dataSource;

    public UserDao() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");
        dataSource = new HikariDataSource(config);
    }

    public List<String> getEmails() {
        List<String> emails = new ArrayList<>();
        try (Statement st = dataSource.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT email FROM users");
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return emails;
    }

    public boolean insertUser(UserModel userModel) {
        try (PreparedStatement psst = dataSource.getConnection().prepareStatement
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
        try (PreparedStatement psst = dataSource.getConnection().prepareStatement
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

    public List<AccountModel> getListAccount(int userId) {
        List<AccountModel> accounts = new ArrayList<>();
        try (Statement st = dataSource.getConnection().createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM account WHERE user_id = " + userId);
            while (rs.next()) {
                accounts.add(new AccountModel(rs.getString("name"), rs.getBigDecimal("balance")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public boolean insertAccount(int userId, AccountModel accountModel) {
        try (PreparedStatement psst = dataSource.getConnection().prepareStatement
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
        try (PreparedStatement psst = dataSource.getConnection().prepareStatement
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
