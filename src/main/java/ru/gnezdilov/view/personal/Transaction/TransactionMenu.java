package ru.gnezdilov.view.personal.Transaction;

import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.DataSourceException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class TransactionMenu {
    private InputRequest inputRequest;
    private UIUtils utils;
    private TransactionActionMenu transactionActionMenu;

    public TransactionMenu(InputRequest inputRequest, UIUtils utils, TransactionActionMenu transactionActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.transactionActionMenu = transactionActionMenu;
    }

    public void start() {
        String answer;
        System.out.println("Welcome to transaction action menu " + ViewFactory.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Show all transaction" +
                            "\n2. Create transaction" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    transactionActionMenu.showAll();
                } else if (answer.equals("2")) {
                    transactionActionMenu.create();
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                     DataSourceException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
