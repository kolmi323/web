package ru.gnezdilov.view.auth;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

@Component
public class RegisterWindow {
    private final UIUtils utils;
    private final PersonalOfficePage personalOfficePage;
    private final AuthService authService;
    private UserDTO currentUser;

    public RegisterWindow(UIUtils utils, PersonalOfficePage personalOfficePage, AuthService authService) {
        this.utils = utils;
        this.personalOfficePage = personalOfficePage;
        this.authService = authService;
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
            currentUser = authService.createNewUser(name, email, password);
            System.out.println("User created");
            personalOfficePage.start(currentUser);
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | DataSourceException | ExitException e ) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
