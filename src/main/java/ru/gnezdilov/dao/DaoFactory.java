package ru.gnezdilov.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DaoFactory {
    private static DaoFactory instance;

    private DataSource dataSource;
    private UserDAO userDao;
    private AccountDAO accountDao;
    private TypeTransactionDAO typeTransactionDao;

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    private DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DataBaseConnectionData.getURL());
            config.setUsername(DataBaseConnectionData.getUser());
            config.setPassword(DataBaseConnectionData.getPassword());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    public UserDAO getUserDAO() {
        if (userDao == null) {
            userDao = new UserDAO(getDataSource());
        }
        return userDao;
    }

    public AccountDAO getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDAO(getDataSource());
        }
        return accountDao;
    }

    public TypeTransactionDAO getTypeTransactionDao() {
        if (typeTransactionDao == null) {
            typeTransactionDao = new TypeTransactionDAO(getDataSource());
        }
        return typeTransactionDao;
    }
}
