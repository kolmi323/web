package web.view.PersonalOffice;

import web.view.InputRequest;
import web.view.UIUtils;
import web.view.ViewFactory;

public class AccountMenu {
    private final UIUtils utils;
    private final InputRequest inputRequest;

    public AccountMenu() {
        this.utils = new UIUtils();
        this.inputRequest = new InputRequest();
    }

    public void startActionMenu() {
        String answer;
        System.out.println("Welcome to account action menu " + ViewFactory.getCurrentUser().getName() + "!");
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
                    ViewFactory.getAccountActionMenu().showListAccount();
                } else if (answer.equals("2")) {
                    ViewFactory.getAccountActionMenu().createAccount();
                } else if (answer.equals("3")) {
                    ViewFactory.getAccountActionMenu().removeAccount();
                } else if (this.utils.isExitAction(answer)) {
                    System.out.println("You exit from account action menu!");
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
