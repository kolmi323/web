package s03.service.StartPage;

import s03.dao.Model.UserModel;
import s03.service.AbstractClass.Service;
import s03.service.CustomInterface.DigestService;
import s03.service.DTO.ServiceDTO;
import s03.service.MD5DigestUtils;

public class RegisterService extends Service {
    private DigestService digestUtils;

    public RegisterService(ServiceDTO service) {
        super(service);
        this.digestUtils = new MD5DigestUtils();
    }

    public boolean createNewUser (String name, String email, String password) {
        UserModel userModel = new UserModel(name, email, digestUtils.hashPassword(password));
        return this.managmentDAO.userDAO.insertUser(userModel);
    }
}
