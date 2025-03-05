package ru.gnezdilov.view.personal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.InputRequest;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.personal.Account.AccountMenu;
import ru.gnezdilov.view.personal.CategoryTransaction.CategoryTransactionMenu;
import ru.gnezdilov.view.personal.Transaction.TransactionMenu;
import ru.gnezdilov.view.personal.Type.TypeMenu;

@Component
public class PersonalOfficePage {
    private final InputRequest inputRequest;
    private final UIUtils utils;
    private final AccountMenu accountMenu;
    private final TypeMenu typeMenu;
    private final CategoryTransactionMenu categoryTransactionMenu;
    private final TransactionMenu transactionMenu;
    private final CurrentUser currentUser;

    public PersonalOfficePage(InputRequest inputRequest, UIUtils utils,
                              AccountMenu accountMenu, TypeMenu typeMenu,
                              CategoryTransactionMenu categoryTransactionMenu, TransactionMenu transactionMenu, CurrentUser currentUser) {
        this.inputRequest = inputRequest;
        this.utils = utils;
        this.accountMenu = accountMenu;
        this.typeMenu = typeMenu;
        this.categoryTransactionMenu = categoryTransactionMenu;
        this.transactionMenu = transactionMenu;
        this.currentUser = currentUser;
    }

    public void start() {
        String answer;
        System.out.println("Welcome to personal office " + currentUser.getCurrentUser().getName() + "!");
        while (true) {
            answer = this.inputRequest.requestStr(
                    "Please choice action menu:" +
                            "\n1. Account action" +
                            "\n2. Type transaction action" +
                            "\n3. Category Transaction action" +
                            "\n4. Transaction action" +
                            "\nexit. Exit"
            );
            if (answer.equals("1")) {
                accountMenu.start();
            } else if (answer.equals("2")) {
                typeMenu.start();
            } else if (answer.equals("3")) {
                categoryTransactionMenu.start();
            } else if (answer.equals("4")) {
                transactionMenu.start();
            } else if (utils.isExitAction(answer)) {
                System.out.println("You exit from person office!");
                return;
            } else {
                System.out.println("Invalid input");
            }
        }
    }
}
