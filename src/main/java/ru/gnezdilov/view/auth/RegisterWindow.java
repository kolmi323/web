package ru.gnezdilov.view.auth;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

@Component
public class RegisterWindow {
    private final UIUtils utils;
    private final PersonalOfficePage personalOfficePage;
    private final AuthService authService;
    private final CurrentUser currentUser;

    public RegisterWindow(UIUtils utils, PersonalOfficePage personalOfficePage, AuthService authService, CurrentUser currentUser) {
        this.utils = utils;
        this.personalOfficePage = personalOfficePage;
        this.authService = authService;
        this.currentUser = currentUser;
    }

    public void register() {
        try {
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
            UserDTO user = authService.createNewUser(name, email, password);
            System.out.println("User created");
            currentUser.setCurrentUser(user);
            personalOfficePage.start();
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | DataSourceException | ExitException e ) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
