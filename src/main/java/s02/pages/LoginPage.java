package s02.pages;

import s02.ActionUserExitException;
import s02.Authorization;
import s02.InputRequest;
import s02.Registration;

import java.sql.Connection;

public class LoginPage {
    private Connection con;
    private InputRequest inputRequest = new InputRequest();

    public LoginPage(Connection con) {
        this.con = con;
    }

    public void startPage() throws ActionUserExitException {
        System.out.println("Welcome to the Login Page");
        String answer;
        while (true) {
            System.out.println(
                    "Do you want to register or log in? " +
                    "\nIf you want exit, enter \"exit\""
            );
            answer = this.inputRequest.requestStr("R/L/exit: ");
            if (answer.equals("R")) {
                Registration registration = new Registration(con);
                registration.register();
            } else if (answer.equals("L")) {
                Authorization authorization = new Authorization(con);
                authorization.logIn();
                int userId = authorization.getUserId();
                if (userId != 0) {
                    try {
                        PersonalOfficePage personalOfficePage = new PersonalOfficePage(con, userId);
                        personalOfficePage.startPersonalOffice();
                    } catch (ActionUserExitException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else if (answer.equals("exit")) {
                throw new ActionUserExitException("You exit from site. Good luck!");
            } else {
                System.out.println(answer + " - is a wrong answer, try again");
            }
        }
    }
}
