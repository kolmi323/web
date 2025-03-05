package ru.gnezdilov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.DataSourceException;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DaoConfiguration {
    private final String URL = System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/postgres");
    private final String USER = System.getProperty("jdbcUser", "postgres");
    private final String PASSWORD = System.getProperty("jdbcPassword", "postgres");

    @Bean
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10);
        DataSource dataSource = new HikariDataSource(config);
        initDataBase(dataSource);
        return dataSource;
    }

    private static void initDataBase(DataSource dataSource) {
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
