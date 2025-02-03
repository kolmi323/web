package ru.gnezdilov.view.auth;

import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;
import ru.gnezdilov.dao.exception.*;

public class LoginWindow {
    private final UIUtils utils;
    private final AuthService authService;
    private final PersonalOfficePage personalOfficePage;

    public LoginWindow(UIUtils utils, AuthService authService, PersonalOfficePage personalOfficePage) {
        this.utils = utils;
        this.authService = authService;
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
            UserDTO user = authService.authorization(email, password);
            System.out.println("Logged in successfully");
            ViewFactory.setCurrentUser(user);
            personalOfficePage.start();
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | DataSourceException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
