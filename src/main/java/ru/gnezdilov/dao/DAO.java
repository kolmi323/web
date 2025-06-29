package ru.gnezdilov.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import javax.sql.DataSource;

@Getter
@Setter
public abstract class DAO {
    protected static final String UNIQUE_CONSTRAINT_VIOLATION = "23505";
    protected EntityManager em;
    private DataSource dataSource;

    protected DAO(DataSource dataSource, EntityManagerFactory emf) {
        this.dataSource = dataSource;
        this.em = emf.createEntityManager();
    }

    protected DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected boolean isUniqueSQLState(PersistenceException e) {
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
            if (cve.getSQLState().equals(UNIQUE_CONSTRAINT_VIOLATION)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
