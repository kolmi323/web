package web.view.PersonalOffice;

import web.view.InputRequest;
import web.view.UIUtils;
import web.view.ViewFactory;

public class PersonalOfficePage {
    private final InputRequest inputRequest;
    private final UIUtils utils;

    public PersonalOfficePage() {
        this.inputRequest = new InputRequest();
        this.utils = new UIUtils();
    }

    public void startPage() {
        String answer;
        System.out.println("Welcome to personal office " + ViewFactory.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Please choice action menu:" +
                            "\n1. Account action" +
                            "\n2. Type transaction action" +
                            "\nexit. Exit"
            );
            if (answer.equals("1")) {
                ViewFactory.getAccountMenu().startActionMenu();
            } else if (answer.equals("2")) {
                ViewFactory.getTypeTransactionMenu().startTypeTransactionMenu();
            } else if (utils.isExitAction(answer)) {
                System.out.println("You exit from person office!");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
