package ru.gnezdilov.view.auth;

import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.view.personal.PersonalOfficePage;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;
import ru.gnezdilov.dao.exception.*;

public class RegisterWindow {
    private final UIUtils utils;
    private final PersonalOfficePage personalOfficePage;
    private final AuthService authService;

    public RegisterWindow(UIUtils utils, PersonalOfficePage personalOfficePage, AuthService authService) {
        this.utils = utils;
        this.personalOfficePage = personalOfficePage;
        this.authService = authService;
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
            UserDTO user = authService.createNewUser(name, email, password);
            System.out.println("User created");
            ViewFactory.setCurrentUser(user);
            personalOfficePage.start();
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
