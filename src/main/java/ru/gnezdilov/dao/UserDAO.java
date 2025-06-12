package ru.gnezdilov.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.entities.User;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.model.UserModel;

import javax.persistence.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
public class UserDAO extends DAO {
    private static final String UNIQUE_CONSTRAINT_VIOLATION = "23505";
    @PersistenceUnit
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public UserDAO(DataSource ds, EntityManagerFactory emf) {
        super(ds);
        this.emf = emf;
        this.em = emf.createEntityManager();
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
            if (UNIQUE_CONSTRAINT_VIOLATION.equals(e.getSQLState())) {
                throw new AlreadyExistsException("Email already exists");
            } else {
                throw new DAOException(e);
            }
        }
    }

    public Optional<UserModel> findByEmailAndPassword(String email, String password) {
        User user = em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getSingleResult();

        return Optional.of(new UserModel(user.getId(), user.getName(), user.getEmail(), user.getPassword()));
    }

    /*public Optional<UserModel> findByEmailAndPassword(String email, String password) {
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
            throw new DAOException(e);
        }
        return Optional.empty();
    }*/

    public UserModel findById(int id) {
        try (Connection con = getDataSource().getConnection();
        PreparedStatement psst = con.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            psst.setInt(1, id);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                if (rs.next()) {
                    return new UserModel(rs.getInt("id"), rs.getString("name"),
                            rs.getString("email"), rs.getString("password"));
                } else {
                    throw new NotFoundException("User not found");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public boolean existsById(int id) {
        try (Connection con = getDataSource().getConnection();
        PreparedStatement psst = con.prepareStatement("SELECT EXISTS(SELECT FROM users WHERE id = ?)")) {
            psst.setInt(1, id);
            psst.executeQuery();
            try (ResultSet rs = psst.getResultSet()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
