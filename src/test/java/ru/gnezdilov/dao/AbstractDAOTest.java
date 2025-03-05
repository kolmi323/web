package ru.gnezdilov.dao;

import org.junit.After;
import org.junit.Before;
import ru.gnezdilov.dao.abstractclass.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*public abstract class AbstractDAOTest <T extends DAO> {
    protected T subj;

    private final String CLEAN_H2_SQL = "DROP ALL OBJECTS";

    @Before
    public void setUp() throws Exception {
        DaoConfiguration.getInstance().getDataSource();
        subj.setDataSource(DaoConfiguration.getInstance().getDataSource());
    }

    @After
    public void tearDown() throws Exception {
        try (PreparedStatement psst =  subj.getDataSource().getConnection().prepareStatement(CLEAN_H2_SQL)) {
            psst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        subj.setDataSource(null);
        DaoConfiguration.getInstance().setDataSource(null);
    }

    protected void setPropertyForConnectH2() {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:test_mem");
        System.setProperty("jdbcUser", "test");
        System.setProperty("jdbcPassword", "test");
        System.setProperty("liquibaseFile", "liquibase_user_dao_test.xml");
    }
}*/
