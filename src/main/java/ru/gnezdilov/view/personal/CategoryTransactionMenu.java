package ru.gnezdilov.view.personal;

import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class CategoryTransactionMenu {
    private InputRequest inputRequest;
    private UIUtils utils;
    private CategoryTransactionActionMenu categoryTransactionActionMenu;

    public CategoryTransactionMenu(InputRequest inputRequest, UIUtils utils, CategoryTransactionActionMenu categoryTransactionActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.categoryTransactionActionMenu = categoryTransactionActionMenu;
    }

    public void start() {
        String answer;
        System.out.println("Welcome to transaction action menu " + ViewFactory.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of incoming transaction" +
                            "\n2. Display a list of outgoing transaction" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    categoryTransactionActionMenu.showAllIncoming();
                } else if (answer.equals("2")) {
                    categoryTransactionActionMenu.showAllOutgoing();
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from type transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
