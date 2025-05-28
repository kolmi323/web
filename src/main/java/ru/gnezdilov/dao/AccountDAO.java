package ru.gnezdilov.dao;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.AccountModel;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDAO extends DAO {
    private static final String UNIQUE_CONSTRAINT_VIOLATION = "23505";

    public AccountDAO(DataSource ds) {
        super(ds);
    }

    public boolean existsById(int id, int userId) {
        try(Connection con = getDataSource().getConnection();
            PreparedStatement psst = con.prepareStatement("SELECT EXISTS(SELECT 1 FROM account WHERE id = ? AND user_id = ?)")) {
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

    public AccountModel findById(int id, int userId) {
        try(Connection con = getDataSource().getConnection();
            PreparedStatement psst = con.prepareStatement("SELECT * FROM account WHERE id = ? AND user_id = ?")) {
            psst.setInt(1, id);
            psst.setInt(2, userId);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                if (rs.next()) {
                    return new AccountModel(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("name"), rs.getBigDecimal("balance"));
                } else {
                    throw new NotFoundException("Account with id " + id + " - not found");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    public List<AccountModel> getAll(int userId) {
        List<AccountModel> accounts = new ArrayList<>();
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("SELECT * FROM account WHERE user_id = ?")) {
            psst.setInt(1, userId);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                while (rs.next()) {
                    accounts.add(new AccountModel(rs.getInt("id"), rs.getInt("user_id"),
                            rs.getString("name"), rs.getBigDecimal("balance")));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return accounts;
    }

    public AccountModel insert(int userId, String name, BigDecimal balance) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con
                     .prepareStatement("INSERT INTO account (user_id, name, balance) VALUES (?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            psst.setInt(1, userId);
            psst.setString(2, name);
            psst.setBigDecimal(3, balance);
            psst.executeUpdate();
            try (ResultSet rs = psst.getGeneratedKeys()) {
                if (rs.next()) {
                    return new AccountModel(rs.getInt(1), userId, name, balance);
                } else {
                    throw new DAOException("Insert account failed");
                }
            }
        }
        catch (SQLException e) {
            if (UNIQUE_CONSTRAINT_VIOLATION.equals(e.getSQLState())) {
                throw new AlreadyExistsException("Account already exists");
            } else {
                throw new DAOException(e);
            }
        }
    }

    public boolean delete(int id, int userId) {
        try (Connection con = getDataSource().getConnection();
             PreparedStatement psst = con.prepareStatement("DELETE FROM account WHERE id = ? AND user_id = ?")) {
            psst.setInt(1, id);
            psst.setInt(2, userId);
            return psst.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
