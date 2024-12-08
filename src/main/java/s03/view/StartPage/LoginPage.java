package s03.view.StartPage;

import s03.CustomException.RegisterDataException;
import s03.service.DTO.ServiceDTO;
import s03.view.CustomInterface.Page;
import s03.view.PersonalPage.PersonalOfficePage;
import s03.view.UserDataControl.ControlExit;
import s03.view.EnterUserData.InputRequest;

public class LoginPage implements Page {
    private final InputRequest inputRequest;
    private final ControlExit controlExit;
    ServiceDTO service;

    public LoginPage(ServiceDTO service) {
        this.inputRequest = new InputRequest();
        controlExit = new ControlExit();
        this.service = service;
    }

    @Override
    public void startPage () {
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
                        PersonalOfficePage personalOffice = new PersonalOfficePage(service, loginWindow.getUserDTO());
                        personalOffice.startPage();
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

