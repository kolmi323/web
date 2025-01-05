package ru.gnezdilov.view.auth;

import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.auth.Registration;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class RegisterWindow {
    private final UIUtils utils;
    private final PersonalOfficePage personalOfficePage;
    private final Registration registration;

    public RegisterWindow(UIUtils utils, PersonalOfficePage personalOfficePage, Registration registration) {
        this.utils = utils;
        this.personalOfficePage = personalOfficePage;
        this.registration = registration;
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
            UserDTO user = registration.createNewUser(name, email, password);
            System.out.println("User created");
            ViewFactory.setCurrentUser(user);
            personalOfficePage.start();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
