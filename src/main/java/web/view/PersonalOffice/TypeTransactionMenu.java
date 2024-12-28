package web.view.PersonalOffice;

import web.view.InputRequest;
import web.view.UIUtils;
import web.view.ViewFactory;

public class TypeTransactionMenu {
    private final InputRequest inputRequest;
    private final UIUtils utils;

    public TypeTransactionMenu() {
        this.inputRequest = new InputRequest();
        this.utils = new UIUtils();
    }

    public void startTypeTransactionMenu() {
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
                    ViewFactory.getTypeTransactionActionMenu().showListTypeTransaction();
                } else if (answer.equals("2")) {
                    ViewFactory.getTypeTransactionActionMenu().updateTypeTransaction();
                } else if (answer.equals("3")) {
                    ViewFactory.getTypeTransactionActionMenu().createTypeTransaction();
                } else if (answer.equals("4")) {
                    ViewFactory.getTypeTransactionActionMenu().removeTypeTransaction();
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
