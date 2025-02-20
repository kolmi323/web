package ru.gnezdilov.dao;

import javax.sql.DataSource;

public final class DaoFactory {
    private static DaoFactory instance;

    private DataSource dataSource;
    private UserDAO userDao;
    private AccountDAO accountDao;
    private TypeDAO typeDao;
    private CategoryTransactionDAO categoryTransactionDao;
    private TransactionDAO transactionDao;

    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
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

    public TypeDAO getTypeDao() {
        if (typeDao == null) {
            typeDao = new TypeDAO(getDataSource());
        }
        return typeDao;
    }

    public CategoryTransactionDAO getCategoryTransactionDao() {
        if (categoryTransactionDao == null) {
            categoryTransactionDao = new CategoryTransactionDAO(getDataSource());
        }
        return categoryTransactionDao;
    }

    public TransactionDAO getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = new TransactionDAO(getDataSource(), getCategoryTransactionDao(), getAccountDao(),
                    getTypeDao());
        }
        return transactionDao;
    }

    private DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = DataSourceFactory.getInstance().getDataSource();
        }
        return dataSource;
    }
}
