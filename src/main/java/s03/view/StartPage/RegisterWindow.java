package s03.view.StartPage;

import s03.CustomException.RegisterDataException;
import s03.service.StartPage.RegisterService;
import s03.service.DTO.ServiceDTO;
import s03.view.EnterUserData.UserDataReceiver;
import s03.view.UserDataControl.ControlExit;

public class RegisterWindow {
    private final RegisterService service;
    private final UserDataReceiver userDataReceiver;
    private final ControlExit controlExit;

    public RegisterWindow(ServiceDTO service) {
        this.service = new RegisterService(service);
        this.userDataReceiver = new UserDataReceiver(service);
        this.controlExit = new ControlExit();
    }

    public void register() throws RegisterDataException {
        String name = this.userDataReceiver.enterName();
        if (this.controlExit.isExitAction(name)) {
            return;
        }
        String email = this.userDataReceiver.enterEmail(true);
        if (this.controlExit.isExitAction(email)) {
            return;
        }
        String password = this.userDataReceiver.enterPassword();
        if (this.controlExit.isExitAction(password)) {
            return;
        }
        if (service.createNewUser(name, email, password)) {
            System.out.println("User " + name + " has been registered successfully.");
        } else {
            throw new RegisterDataException();
        }
    }
}
