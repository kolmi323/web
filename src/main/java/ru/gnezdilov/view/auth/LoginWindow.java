package ru.gnezdilov.view.auth;

import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.auth.Authorization;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class LoginWindow {
    private final UIUtils utils;
    private final Authorization authorization;
    private final PersonalOfficePage personalOfficePage;

    public LoginWindow(UIUtils utils, Authorization authorization, PersonalOfficePage personalOfficePage) {
        this.utils = utils;
        this.authorization = authorization;
        this.personalOfficePage = personalOfficePage;
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
            UserDTO user = authorization.authorization(email, password);
            System.out.println("Logged in successfully");
            ViewFactory.setCurrentUser(user);
            personalOfficePage.start();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
