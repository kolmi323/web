package ru.gnezdilov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.gnezdilov.dao.exception.DataSourceException;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DataSourceFactory {
    private static DataSourceFactory instance;
    private static DataSource dataSource;

    private final String URL = System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/postgres");
    private final String USER = System.getProperty("jdbcUser", "postgres");
    private final String PASSWORD = System.getProperty("jdbcPassword", "postgres");

    public static DataSourceFactory getInstance() {
        if (instance == null) {
            instance = new DataSourceFactory();
        }
        return instance;
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            dataSource = new HikariDataSource(config);
            initDataBase();
        }
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static void initDataBase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(System.getProperty("liquibaseFile", "liquibase.xml"),
                    new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new DataSourceException(e);
        }
    }
}
