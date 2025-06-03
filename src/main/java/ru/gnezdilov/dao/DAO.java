package ru.gnezdilov.dao;

import javax.sql.DataSource;

public abstract class DAO {
    private DataSource dataSource;

    protected DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
