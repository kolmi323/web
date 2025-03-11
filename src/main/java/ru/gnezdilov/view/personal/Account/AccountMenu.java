package ru.gnezdilov.view.personal.Account;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.dao.exception.*;

@Component
public class AccountMenu {
    private final UIUtils utils;
    private final InputRequest inputRequest;
    private final AccountActionMenu accountActionMenu;

    public AccountMenu(UIUtils utils, InputRequest inputRequest, AccountActionMenu accountActionMenu) {
        this.utils = utils;
        this.inputRequest = inputRequest;
        this.accountActionMenu = accountActionMenu;
    }

    public void start(UserDTO currentUser) {
        String answer;
        System.out.println("Welcome to account action menu " + currentUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of accounts" +
                            "\n2. Create account" +
                            "\n3. Delete account" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    accountActionMenu.showAll(currentUser);
                } else if (answer.equals("2")) {
                    accountActionMenu.createAccount(currentUser);
                } else if (answer.equals("3")) {
                    accountActionMenu.removeAccount(currentUser);
                } else if (this.utils.isExitAction(answer)) {
                    System.out.println("You exit from account action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | DataSourceException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
