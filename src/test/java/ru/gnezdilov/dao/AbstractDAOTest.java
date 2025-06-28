package ru.gnezdilov.dao;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAOTest <T> {
    protected T subj;
    protected ApplicationContext context;

    private final String CLEAN_H2_SQL = "DROP ALL OBJECTS";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        try (PreparedStatement psst =  context.getBean(DataSource.class).getConnection().prepareStatement(CLEAN_H2_SQL)) {
            psst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void setPropertyForConnectH2() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem");
        System.setProperty("jdbcUser", "test");
        System.setProperty("jdbcPassword", "test");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");
    }
}
