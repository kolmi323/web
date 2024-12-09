package s03.service.AbstractClass;

import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.dao.AbstractClass.Model;

public abstract class ActionControlService <M extends Model> extends Service {
    protected final UserDTO currentUser;

    public ActionControlService(ServiceDTO service, UserDTO currentUser) {
        super(service);
        this.currentUser = currentUser;
    }

    public abstract boolean create(M model);

    public abstract boolean delete(M model);
}
