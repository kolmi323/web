package web.view.StartPage;

import web.view.InputRequest;
import web.view.UIUtils;
import web.view.ViewFactory;

public class LoginPage {
    private final InputRequest inputRequest;
    private final UIUtils utils;

    public LoginPage() {
        this.inputRequest = new InputRequest();
        this.utils = new UIUtils();
    }

    public void startPage () {
        String answer;
        while (true) {
            System.out.println(
                    "Do you want to register or log in? " +
                            "\nIf you want exit, enter \"exit\""
            );
            answer = this.inputRequest.requestStr("\"R/L/exit: \"");
            if (answer.equalsIgnoreCase("r")) {
                ViewFactory.getRegisterWindow().register();
            } else if (answer.equalsIgnoreCase("l")) {
                ViewFactory.getLoginWindow().login();
            } else if (utils.isExitAction(answer)) {
                return;
            } else {
                System.out.println(answer + " - is a wrong answer, try again");
            }
        }
    }
}
