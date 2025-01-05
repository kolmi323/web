package ru.gnezdilov.service.auth;

import ru.gnezdilov.dao.customexception.NotFoundException;
import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.dao.model.UserModel;
import ru.gnezdilov.service.dto.UserDTO;

import java.util.Optional;

public class Authorization {
    private final UserDAO userDAO;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public Authorization(UserDAO dao, DigestService digestUtils, ConverterUserModelToUserDTO converter) {
        this.userDAO = dao;
        digestService = digestUtils;
        converterUserModelToUserDTO = converter;
    }

    public UserDTO authorization(String email, String password) {
        Optional<UserModel> userModel = userDAO.entry(email, digestService.hashPassword(password));
        if (userModel.isPresent()) {
            return converterUserModelToUserDTO.convert(userModel.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
