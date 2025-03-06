package ru.gnezdilov.dao;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.dao.abstractclass.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractDAOTest <T extends DAO> {
    protected T subj;
    protected ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);

    private final String CLEAN_H2_SQL = "DROP ALL OBJECTS";

    @Before
    public void setUp() throws Exception {

        subj.setDataSource(context.getBean(DataSourceDAO.class).getDataSource());
    }

    @After
    public void tearDown() throws Exception {
        try (PreparedStatement psst =  subj.getDataSource().getConnection().prepareStatement(CLEAN_H2_SQL)) {
            psst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subj.setDataSource(null);
        context.getBean(DataSourceDAO.class).setDataSource(null);
    }

    protected void setPropertyForConnectH2() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem");
        System.setProperty("jdbcUser", "test");
        System.setProperty("jdbcPassword", "test");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");
    }
}
