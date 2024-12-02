package s03.view.StartPage;

import s03.CustomException.RegisterDataException;
import s03.service.Service;
import s03.view.PersonalPage.PersonalOffice;
import s03.view.UserDataControl.ControlExit;
import s03.view.EnterUserData.InputRequest;

public class LoginPage {
    private final InputRequest inputRequest;
    Service service;

    public LoginPage(Service service) {
        this.inputRequest = new InputRequest();
        this.service = service;
    }

    public void startPage () {
        ControlExit controlExit = new ControlExit();
        String answer;
        while (true) {
            System.out.println(
                    "Do you want to register or log in? " +
                            "\nIf you want exit, enter \"exit\""
            );
            answer = this.inputRequest.requestStr("\"R/L/exit: \"");
            try {
                if (answer.equalsIgnoreCase("r")) {
                    RegisterWindow registerWindow = new RegisterWindow(this.service);
                    registerWindow.register();
                } else if (answer.equalsIgnoreCase("l")) {
                    LoginWindow loginWindow = new LoginWindow(this.service);
                    loginWindow.login();
                    if (loginWindow.getUserDTO().getId() != 0) {
                        PersonalOffice personalOffice = new PersonalOffice(service, loginWindow.getUserDTO());
                        personalOffice.startPersonalOffice();
                    }
                } else if (controlExit.isExitAction(answer)) {
                    return;
                } else {
                    System.out.println(answer + " - is a wrong answer, try again");
                }
            } catch (RegisterDataException rde) {
                System.out.println(rde.getMessage());
            }
        }
    }
}

