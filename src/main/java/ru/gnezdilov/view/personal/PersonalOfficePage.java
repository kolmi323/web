package ru.gnezdilov.view.personal;

import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class PersonalOfficePage {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final AccountMenu accountMenu;
    private final TypeMenu typeMenu;
    private final TransactionMenu transactionMenu;

    public PersonalOfficePage(InputRequest inputRequest, UIUtils utils,
                              AccountMenu accountMenu, TypeMenu typeMenu, TransactionMenu transactionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.accountMenu = accountMenu;
        this.typeMenu = typeMenu;
        this.transactionMenu = transactionMenu;
    }

    public void start() {
        String answer;
        System.out.println("Welcome to personal office " + ViewFactory.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Please choice action menu:" +
                            "\n1. Account action" +
                            "\n2. Type transaction action" +
                            "\n3. Transaction action" +
                            "\nexit. Exit"
            );
            if (answer.equals("1")) {
                accountMenu.start();
            } else if (answer.equals("2")) {
                typeMenu.start();
            } else if (answer.equals("3")) {
                transactionMenu.start();
            }
            else if (utils.isExitAction(answer)) {
                System.out.println("You exit from person office!");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
