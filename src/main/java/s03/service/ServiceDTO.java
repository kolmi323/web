package s03.service;

import s03.dao.UserDao;
import s03.service.AbstractClass.Service;

public class ServiceDTO extends Service {
    public ServiceDTO() {
        super();
    }

    @Override
    public UserDao getUserDao() {
        return super.getUserDao();
    }
}
