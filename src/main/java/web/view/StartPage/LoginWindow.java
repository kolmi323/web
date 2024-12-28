package web.view.StartPage;

import web.service.DTO.UserDTO;
import web.service.ServiceFactory;
import web.view.UIUtils;
import web.view.ViewFactory;

public class LoginWindow {
    private final UIUtils utils;

    public LoginWindow() {
        this.utils = new UIUtils();
    }

    public void login() {
        String email = this.utils.enterEmail();
        if (this.utils.isExitAction(email)) {
            return;
        }
        String password = this.utils.enterPassword();
        if (this.utils.isExitAction(password)) {
            return;
        }
        try {
            UserDTO user = ServiceFactory.getAuthorization().authorization(email, password);
            System.out.println("Logged in successfully");
            ViewFactory.setCurrentUser(user);
            ViewFactory.getPersonalOfficePage().startPage();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
