package web.service.StartPage;

import web.CustomException.UserNotFoundException;
import web.converter.ConverterUserModelToUserDTO;
import web.dao.Model.UserModel;
import web.dao.UserDAO;
import web.service.CustomInterface.DigestService;
import web.service.DTO.UserDTO;
import web.service.MD5DigestUtils;

import java.util.Optional;

public class Authorization {
    private final UserDAO userDAO;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public Authorization(UserDAO dao) {
        this.userDAO = dao;
        digestService = new MD5DigestUtils();
        converterUserModelToUserDTO = new ConverterUserModelToUserDTO();
    }

    public UserDTO authorization(String email, String password) {
        Optional<UserModel> userModel = userDAO.entryUser(email, digestService.hashPassword(password));
        if (userModel.isPresent()) {
            return converterUserModelToUserDTO.convert(userModel.get());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
