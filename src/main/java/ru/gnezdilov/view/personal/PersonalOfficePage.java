package ru.gnezdilov.view.personal;

import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

public class PersonalOfficePage {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final AccountMenu accountMenu;
    private final TypeTransactionMenu typeTransactionMenu;

    public PersonalOfficePage(InputRequest inputRequest, UIUtils utils,
                              AccountMenu accountMenu, TypeTransactionMenu typeTransactionMenu) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.accountMenu = accountMenu;
        this.typeTransactionMenu = typeTransactionMenu;
    }

    public void start() {
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
                accountMenu.start();
            } else if (answer.equals("2")) {
                typeTransactionMenu.startAll();
            } else if (utils.isExitAction(answer)) {
                System.out.println("You exit from person office!");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
