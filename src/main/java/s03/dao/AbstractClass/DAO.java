package s03.dao.AbstractClass;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import s03.dao.DataBaseConnectionData;

import javax.sql.DataSource;

public abstract class DAO {
    private final DataSource dataSource;

    protected DAO() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DataBaseConnectionData.getURL());
        config.setUsername(DataBaseConnectionData.getUSER());
        config.setPassword(DataBaseConnectionData.getPASSWORD());
        dataSource = new HikariDataSource(config);
    }

    protected DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }
}
