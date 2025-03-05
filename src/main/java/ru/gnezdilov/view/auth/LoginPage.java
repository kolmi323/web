package ru.gnezdilov.view.auth;

import org.springframework.stereotype.Component;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;

@Component
public class LoginPage {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final RegisterWindow registerWindow;
    private final LoginWindow loginWindow;

    public LoginPage(InputRequest inputRequest, UIUtils utils, RegisterWindow registerWindow, LoginWindow loginWindow) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.registerWindow = registerWindow;
        this.loginWindow = loginWindow;
    }

    public void start() {
        String answer;
        while (true) {
            System.out.println(
                    "Do you want to register or log in? " +
                            "\nIf you want exit, enter \"exit\""
            );
            answer = this.inputRequest.requestStr("\"R/L/exit: \"");
            if (answer.equalsIgnoreCase("r")) {
                registerWindow.register();
            } else if (answer.equalsIgnoreCase("l")) {
                loginWindow.login();
            } else if (utils.isExitAction(answer)) {
                return;
            } else {
                System.out.println(answer + " - is a wrong answer, try again");
            }
        }
    }
}
