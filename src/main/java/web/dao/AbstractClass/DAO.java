package web.dao.AbstractClass;

import javax.sql.DataSource;

public abstract class DAO {
    private final DataSource dataSource;

    protected DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }
}
