package ru.gnezdilov.view.personal.Transaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;

@Component
public class TransactionMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final TransactionActionMenu transactionActionMenu;

    public TransactionMenu(InputRequest inputRequest, UIUtils utils, TransactionActionMenu transactionActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.transactionActionMenu = transactionActionMenu;
    }

    public void start(UserDTO currentUser) {
        String answer;
        System.out.println("Welcome to transaction action menu " + currentUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Create transaction" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    transactionActionMenu.create(currentUser);
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (NotFoundException | DAOException | NullPointerException | InsufficientFundsException | IllegalArgumentException | ExitException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
