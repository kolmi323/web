package s02.pages;

import s02.*;

import java.sql.Connection;

public class LoginPage {
    private Connection con;
    private InputRequest inputRequest;
    private ControlExit controlExit;

    public LoginPage(Connection con) {
        this.con = con;
        inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
    }

    public void startPage() {
        System.out.println("Welcome to the Login Page");
        String answer;
        while (true) {
            System.out.println(
                    "Do you want to register or log in? " +
                    "\nIf you want exit, enter \"exit\""
            );
            answer = this.inputRequest.requestStr("R/L/exit: ");
            if (answer.equalsIgnoreCase("r")) {
                Registration registration = new Registration(con);
                registration.register();
            } else if (answer.equalsIgnoreCase("l")) {
                Authorization authorization = new Authorization(con);
                authorization.logIn();
                int userId = authorization.getUserId();
                try {
                    if (userId != 0) {
                        PersonalOfficePage personalOfficePage = new PersonalOfficePage(con, userId);
                        personalOfficePage.startPersonalOffice();
                    }
                } catch (BadCredentialsException bce) {
                    System.out.println(bce.getMessage());
                }
            } else if (controlExit.isExit(answer)) {
                System.out.println(("You exit from site. Good luck!"));
                return;
            } else {
                System.out.println(answer + " - is a wrong answer, try again");
            }
        }
    }
}
