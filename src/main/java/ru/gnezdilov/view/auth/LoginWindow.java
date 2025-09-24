package ru.gnezdilov.view.auth;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

import javax.persistence.EntityNotFoundException;

@Component
public class LoginWindow {
    private final UIUtils utils;
    private final AuthService authService;
    private final PersonalOfficePage personalOfficePage;
    private UserDTO currentUser;

    public LoginWindow(UIUtils utils, AuthService authService, PersonalOfficePage personalOfficePage) {
        this.utils = utils;
        this.authService = authService;
        this.personalOfficePage = personalOfficePage;
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
            currentUser = authService.authorization(email, password);
            System.out.println("Logged in successfully");
            personalOfficePage.start(currentUser);
        } catch (EntityNotFoundException | NotFoundException | DAOException | NullPointerException | ExitException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public UserDTO getCurrentUser() {
        return currentUser;
    }
}
