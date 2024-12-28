package web.service.StartPage;

import web.converter.ConverterUserModelToUserDTO;
import web.dao.Model.UserModel;
import web.dao.UserDAO;
import web.service.DTO.UserDTO;
import web.service.CustomInterface.DigestService;
import web.service.MD5DigestUtils;

public class Registration {
    private final UserDAO userDAO;
    private final DigestService digestUtils;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public Registration(UserDAO dao) {
        this.userDAO = dao;
        this.digestUtils = new MD5DigestUtils();
        this.converterUserModelToUserDTO = new ConverterUserModelToUserDTO();
    }

    public UserDTO createNewUser (String name, String email, String password) {
        UserModel user = userDAO.insertUser(name, email, digestUtils.hashPassword(password));
        return converterUserModelToUserDTO.convert(user);
    }
}
