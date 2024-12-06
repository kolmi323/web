package s03.service;

import s03.dao.UserModel;
import s03.service.CustomInterface.DigestService;

public class RegisterService extends ServiceDTO {
    private DigestService digestUtils;

    public RegisterService(ServiceDTO service) {
        this.digestUtils = new MD5DigestUtils();
        this.userDao = service.getUserDao();
    }

    public boolean createNewUser (String name, String email, String password) {
        UserModel userModel = new UserModel(name, email, digestUtils.hashPassword(password));
        return this.userDao.insertUser(userModel);
    }
}
