package s03.service.StartPage;

import s03.converter.ConverterUserModelToUserDTO;
import s03.dao.Model.UserModel;
import s03.service.AbstractClass.Service;
import s03.service.CustomInterface.DigestService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.service.MD5DigestUtils;

public class AuthService extends Service {
    private UserDTO userDTO;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converterUserModelToUserDTO;

    public AuthService(ServiceDTO service) {
        super(service);
        digestService = new MD5DigestUtils();
        converterUserModelToUserDTO = new ConverterUserModelToUserDTO();
    }

    public boolean authorization(String email, String password) {
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(digestService.hashPassword(password));
        if (managmentDAO.userDAO.entryUser(userModel)) {
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
