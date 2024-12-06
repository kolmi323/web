package s03.service.AbstractClass;

import s03.service.ServiceDTO;
import s03.service.UserDTO;

public abstract class ActionControlService <M> extends Service {
    protected final UserDTO currentUser;

    public ActionControlService(ServiceDTO service, UserDTO currentUser) {
        this.userDao = service.getUserDao();
        this.currentUser = currentUser;
    }

    public abstract boolean create(M model);

    public abstract boolean delete(M model);
}
