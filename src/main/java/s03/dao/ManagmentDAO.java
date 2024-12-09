package s03.dao;

import s03.dao.AbstractClass.DAO;

public class ManagmentDAO extends DAO {
    public final UserDAO userDAO;
    public final GetterDAO getterDAO;
    public final AccountDAO accountDAO;
    public final TypeTransactionDAO typeTransactionDAO;

    public ManagmentDAO() {
        super();
        this.userDAO= new UserDAO(getDataSource());
        this.getterDAO = new GetterDAO(getDataSource());
        this.accountDAO = new AccountDAO(getDataSource());
        this.typeTransactionDAO = new TypeTransactionDAO(getDataSource());
    }
}
