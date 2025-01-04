package ru.gnezdilov.view.personal;

import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class TypeTransactionMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final TypeTransactionActionMenu typeTransactionActionMenu;

    public TypeTransactionMenu(InputRequest inputRequest, UIUtils utils,
                               TypeTransactionActionMenu typeTransactionActionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.typeTransactionActionMenu = typeTransactionActionMenu;
    }

    public void startAll() {
        String answer;
        System.out.println("Welcome to type transaction action menu " + ViewFactory.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Action menu:" +
                            "\n1. Display a list of name type" +
                            "\n2. Edit name type" +
                            "\n3. Create type" +
                            "\n4. Delete type" +
                            "\nexit. Exit"
            );
            try {
                if (answer.equals("1")) {
                    typeTransactionActionMenu.showAll();
                } else if (answer.equals("2")) {
                    typeTransactionActionMenu.updateTypeTransaction();
                } else if (answer.equals("3")) {
                    typeTransactionActionMenu.createTypeTransaction();
                } else if (answer.equals("4")) {
                    typeTransactionActionMenu.removeTypeTransaction();
                } else if (utils.isExitAction(answer)) {
                    System.out.println("You exit from type transaction action menu!");
                    return;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
