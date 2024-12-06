package s03.service.AbstractClass;

import s03.dao.UserDao;

public abstract class Service {
    protected UserDao userDao;

    public Service() {
        userDao = new UserDao();
    }

    protected UserDao getUserDao() {
        return userDao;
    }
}
