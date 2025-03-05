package ru.gnezdilov.view.personal.Transaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;

@Component
public class TransactionMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final TransactionActionMenu transactionActionMenu;
    private final CurrentUser currentUser;

    public TransactionMenu(InputRequest inputRequest, UIUtils utils, TransactionActionMenu transactionActionMenu,
                           CurrentUser currentUser) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.transactionActionMenu = transactionActionMenu;
        this.currentUser = currentUser;
    }

    public void start() {
        String answer;
        System.out.println("Welcome to transaction action menu " + currentUser.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Create transaction" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    transactionActionMenu.create();
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                     DataSourceException | InsufficientFundsException | IllegalArgumentException | ExitException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
