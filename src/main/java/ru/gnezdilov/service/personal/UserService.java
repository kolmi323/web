package ru.gnezdilov.service.personal;

import ru.gnezdilov.dao.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean existsById(int id) {
        return userDAO.existsById(id);
    }
}
