package ru.gnezdilov.view.personal.CategoryTransaction;

import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;
import ru.gnezdilov.view.personal.Account.AccountActionMenu;
import ru.gnezdilov.view.personal.Type.TypeActionMenu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map.Entry;

public class CategoryTransactionActionMenu {
    private final UIUtils utils;
    private final CategoryTransactionService categoryTransactionService;
    private final TypeActionMenu typeActionMenu;
    private final AccountActionMenu accountActionMenu;

    public CategoryTransactionActionMenu(UIUtils utils, CategoryTransactionService categoryTransactionService,
                                         TypeActionMenu typeActionMenu, AccountActionMenu accountActionMenu) {
        this.utils = utils;
        this.categoryTransactionService = categoryTransactionService;
        this.typeActionMenu = typeActionMenu;
        this.accountActionMenu = accountActionMenu;
    }

    private void showAll(boolean isIncoming) {
        System.out.println("You must enter the date after the transaction");
        String dateAfter = utils.enterDate();
        if (utils.isExitAction(dateAfter)) {
            return;
        }
        System.out.println("You must enter the date before the transaction");
        String dateBefore = utils.enterDate();
        if (utils.isExitAction(dateBefore)) {
            return;
        }
        HashMap<String, BigDecimal> transactions;
        if (isIncoming) {
            transactions = categoryTransactionService
                    .getIncomingTransactions(ViewFactory.getCurrentUser().getId(),
                            LocalDate.parse(dateAfter), LocalDate.parse(dateBefore));
            System.out.println("Incoming transactions:");
        } else {
            transactions = categoryTransactionService
                    .getOutgoingTransactions(ViewFactory.getCurrentUser().getId(),
                            LocalDate.parse(dateAfter), LocalDate.parse(dateBefore));
            System.out.println("Outgoing transactions:");
        }
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            showTransactionResult(transactions);
        }
    }

    private void showTransactionResult(HashMap<String, BigDecimal> transactions) {
        for (Entry<String, BigDecimal> entry : transactions.entrySet()) {
            System.out.print(entry.getKey() + " - " + entry.getValue() + "; ");
        }
        System.out.println();
    }

    public void showAllIncoming() {
        showAll(true);
    }

    public void showAllOutgoing() {
        showAll(false);
    }
}
