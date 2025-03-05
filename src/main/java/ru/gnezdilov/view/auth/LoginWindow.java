package ru.gnezdilov.view.auth;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

@Component
public class LoginWindow {
    private final UIUtils utils;
    private final AuthService authService;
    private final PersonalOfficePage personalOfficePage;
    private final CurrentUser currentUser;

    public LoginWindow(UIUtils utils, AuthService authService, PersonalOfficePage personalOfficePage, CurrentUser currentUser) {
        this.utils = utils;
        this.authService = authService;
        this.personalOfficePage = personalOfficePage;
        this.currentUser = currentUser;
    }

    public void login() {
        try {
            String email = this.utils.enterEmail();
            if (this.utils.isExitAction(email)) {
                return;
            }
            String password = this.utils.enterPassword();
            if (this.utils.isExitAction(password)) {
                return;
            }
            UserDTO user = authService.authorization(email, password);
            System.out.println("Logged in successfully");
            currentUser.setCurrentUser(user);
            personalOfficePage.start();
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | DataSourceException | ExitException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
