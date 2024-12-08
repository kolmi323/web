package s03.dao;

import s03.CustomException.DAOException;
import s03.dao.AbstractClass.DAO;
import s03.dao.Model.AccountModel;

import java.sql.*;

public class ManagmentDAO extends DAO {
    public final UserDAO userDAO;
    public final GetterDAO getterDAO;
    public final AccountDAO accountDAO;

    public ManagmentDAO() {
        super();
        userDAO = new UserDAO(getDataSource());
        getterDAO = new GetterDAO(getDataSource());
        accountDAO = new AccountDAO(getDataSource());
    }
}
