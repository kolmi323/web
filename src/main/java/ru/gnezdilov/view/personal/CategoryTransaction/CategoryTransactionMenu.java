package ru.gnezdilov.view.personal.CategoryTransaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;

import javax.persistence.EntityNotFoundException;

@Component
public class CategoryTransactionMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final CategoryTransactionActionMenu categoryTransactionActionMenu;

    public CategoryTransactionMenu(InputRequest inputRequest, UIUtils utils, CategoryTransactionActionMenu categoryTransactionActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.categoryTransactionActionMenu = categoryTransactionActionMenu;
    }

    public void start(UserDTO currentUser) {
        String answer;
        System.out.println("Welcome to category transaction action menu " + currentUser.getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of incoming transaction" +
                            "\n2. Display a list of outgoing transaction" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    categoryTransactionActionMenu.showAllIncoming(currentUser);
                } else if (answer.equals("2")) {
                    categoryTransactionActionMenu.showAllOutgoing(currentUser);
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from type transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (EntityNotFoundException | NotFoundException | DAOException | NullPointerException | ExitException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
