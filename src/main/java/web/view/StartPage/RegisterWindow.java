package web.view.StartPage;

import web.service.DTO.UserDTO;
import web.service.ServiceFactory;
import web.view.UIUtils;
import web.view.ViewFactory;

public class RegisterWindow {
    private final UIUtils utils;

    public RegisterWindow() {
        this.utils = new UIUtils();
    }

    public void register() {
        String name = this.utils.enterName();
        if (this.utils.isExitAction(name)) {
            return;
        }
        String email = this.utils.enterEmail();
        if (this.utils.isExitAction(email)) {
            return;
        }
        String password = this.utils.enterPassword();
        if (this.utils.isExitAction(password)) {
            return ;
        }
        try {
            UserDTO user = ServiceFactory.getRegistration().createNewUser(name, email, password);
            System.out.println("User created");
            ViewFactory.setCurrentUser(user);
            ViewFactory.getPersonalOfficePage().startPage();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
