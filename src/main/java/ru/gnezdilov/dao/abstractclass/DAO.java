package ru.gnezdilov.dao.abstractclass;

import ru.gnezdilov.dao.DataSourceDAO;

import javax.sql.DataSource;

public abstract class DAO {
    private DataSource dataSource;

    protected DAO(DataSourceDAO dataSourceDao) {
        this.dataSource = dataSourceDao.getDataSource();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
