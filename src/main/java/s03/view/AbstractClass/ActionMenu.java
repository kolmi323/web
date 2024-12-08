package s03.view.AbstractClass;

import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.view.EnterUserData.InputRequest;
import s03.view.UserDataControl.ControlExit;

public abstract class ActionMenu <T extends ActionControlPanel>{
    protected final InputRequest inputRequest;
    protected final ControlExit controlExit;
    protected final T actionControlPanel;
    protected final UserDTO selectedUser;

    public ActionMenu(ServiceDTO service, UserDTO selectedUser) {
        this.selectedUser = selectedUser;
        this.actionControlPanel = createActionControlPanel(service, selectedUser);
        this.inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
    }

    protected abstract T createActionControlPanel(ServiceDTO service, UserDTO selectedUser);

    public abstract void startActionMenu();
}
