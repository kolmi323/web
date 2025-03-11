package ru.gnezdilov.dao.abstractclass;

import ru.gnezdilov.dao.ConfigurationDAO;

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
