package s03.service;

import s03.dao.UserDao;

import java.util.List;

public class Service {
    protected UserDao userDao;

    public Service() {
        userDao = new UserDao();
    }

    protected UserDao getUserDao() {
        return userDao;
    }
}
