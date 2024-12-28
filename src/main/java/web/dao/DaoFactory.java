package web.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DaoFactory {
    private static DataSource dataSource;
    private static UserDAO userDao;
    private static AccountDAO accountDao;
    private static TypeTransactionDAO typeTransactionDao;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DataBaseConnectionData.getURL());
            config.setUsername(DataBaseConnectionData.getUser());
            config.setPassword(DataBaseConnectionData.getPassword());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

    public static UserDAO getUserDAO() {
        if (userDao == null) {
            userDao = new UserDAO(getDataSource());
        }
        return userDao;
    }

    public  static AccountDAO getAccountDao() {
        if (accountDao == null) {
            accountDao = new AccountDAO(getDataSource());
        }
        return accountDao;
    }

    public static TypeTransactionDAO getTypeTransactionDao() {
        if (typeTransactionDao == null) {
            typeTransactionDao = new TypeTransactionDAO(getDataSource());
        }
        return typeTransactionDao;
    }
}
