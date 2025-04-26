package ru.gnezdilov.service.personal;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.dto.UserDTO;

@Service
public class UserService {
    private UserDAO userDAO;
    private ConverterUserModelToUserDTO converter;

    public UserService(UserDAO userDAO, ConverterUserModelToUserDTO converter) {
        this.userDAO = userDAO;
        this.converter = converter;
    }

    public UserDTO getUserById(int id) {
        return converter.convert(userDAO.findById(id));
    }

    public boolean existsById(int id) {
        return userDAO.existsById(id);
    }
}
