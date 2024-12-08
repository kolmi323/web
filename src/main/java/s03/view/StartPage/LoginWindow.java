package s03.view.StartPage;

import s02.ControlExit;
import s03.service.StartPage.AuthService;
import s03.service.DTO.ServiceDTO;
import s03.service.DTO.UserDTO;
import s03.view.EnterUserData.UserDataReceiver;

public class LoginWindow {
    private final AuthService service;
    private final UserDataReceiver userDataReceiver;
    private final ControlExit controlExit;
    private UserDTO userDTO;

    public LoginWindow(ServiceDTO service) {
        this.service = new AuthService(service);
        this.userDataReceiver = new UserDataReceiver(service);
        this.controlExit = new ControlExit();
        userDTO = new UserDTO();
    }

    public void login() {
        String email = this.userDataReceiver.enterEmail(false);
        if (this.controlExit.isExit(email)) {
            return;
        }
        String password = this.userDataReceiver.enterPassword();
        if (this.controlExit.isExit(password)) {
            return;
        }
        if (this.service.authorization(email, password)) {
            this.userDTO = this.service.getUserDTO();
            System.out.println("Logged in successfully");
        } else {
            System.out.println("Invalid email or password");
        }
    }

    public UserDTO getUserDTO() {
        return this.userDTO;
    }
}
