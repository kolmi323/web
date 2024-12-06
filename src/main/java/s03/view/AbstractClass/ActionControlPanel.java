package s03.view.AbstractClass;

import s03.CustomException.ActionControlException;
import s03.service.AccountActionControlService;
import s03.service.ServiceDTO;
import s03.service.UserDTO;
import s03.view.EnterUserData.UserDataReceiver;
import s03.view.UserDataControl.ControlExit;

public abstract class ActionControlPanel {
    protected final AccountActionControlService service; // заменить на абстрактный класс как сделаю его
    protected final UserDataReceiver userDataReceiver;
    protected final ControlExit controlExit;
    protected final UserDTO selectedUser;

    public ActionControlPanel(ServiceDTO service, UserDTO selectedUser) {
        this.selectedUser = selectedUser;
        this.service = new AccountActionControlService(service, selectedUser);
        this.userDataReceiver = new UserDataReceiver(service);
        this.controlExit = new ControlExit();
    }

    public abstract void add() throws ActionControlException;
    public abstract void remove();
}
