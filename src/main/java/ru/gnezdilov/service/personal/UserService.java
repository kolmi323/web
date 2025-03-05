package ru.gnezdilov.service.personal;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserDAO;

@Service
public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean existsById(int id) {
        return userDAO.existsById(id);
    }
}
