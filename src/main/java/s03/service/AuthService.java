package s03.service;

import s03.converter.ConverterUserModelToUserDTO;
import s03.dao.UserModel;
import s03.service.CustomInterface.DigestService;

public class AuthService extends Service {
    private UserDTO userDTO;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public AuthService(Service service) {
        this.userDao = service.getUserDao();
        digestService = new MD5DigestUtils();
        converterUserModelToUserDTO = new ConverterUserModelToUserDTO();
    }

    public boolean authorization(String email, String password) {
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(digestService.hashPassword(password));
        if (userDao.entryUser(userModel)) {
            this.userDTO = converterUserModelToUserDTO.convert(userModel);
            return true;
        } else {
            return false;
        }
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
