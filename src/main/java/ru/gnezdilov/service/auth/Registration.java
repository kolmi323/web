package ru.gnezdilov.service.auth;

import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.dao.model.UserModel;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.custominterface.DigestService;

public class Registration {
    private final UserDAO userDAO;
    private final DigestService digestUtils;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public Registration(UserDAO dao, DigestService digestUtils, ConverterUserModelToUserDTO converter) {
        this.userDAO = dao;
        this.digestUtils = digestUtils;
        this.converterUserModelToUserDTO = converter;
    }

    public UserDTO createNewUser (String name, String email, String password) {
        UserModel user = userDAO.insert(name, email, digestUtils.hashPassword(password));
        return converterUserModelToUserDTO.convert(user);
    }
}
