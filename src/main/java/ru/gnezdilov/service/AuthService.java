package ru.gnezdilov.service;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.dto.UserDTO;

@Service
public class AuthService {
    private final UserDAO userDAO;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converter;

    public AuthService(UserDAO dao, DigestService digestService, ConverterUserModelToUserDTO converter) {
        this.userDAO = dao;
        this.digestService = digestService;
        this.converter = converter;
    }

    public UserDTO authorization(String email, String password) {
        return userDAO.findByEmailAndPassword(email, digestService.hashPassword(password))
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserDTO createNewUser (String name, String email, String password) {
        UserModel user = userDAO.insert(name, email, digestService.hashPassword(password));
        return converter.convert(user);
    }
}
