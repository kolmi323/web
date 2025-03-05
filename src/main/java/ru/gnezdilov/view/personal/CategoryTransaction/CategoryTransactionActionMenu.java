package ru.gnezdilov.view.personal.CategoryTransaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.personal.Account.AccountActionMenu;
import ru.gnezdilov.view.personal.Type.TypeActionMenu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CategoryTransactionActionMenu {
    private final UIUtils utils;
    private final CategoryTransactionService categoryTransactionService;
    private final CurrentUser currentUser;

    public CategoryTransactionActionMenu(UIUtils utils, CategoryTransactionService categoryTransactionService, CurrentUser currentUser) {
        this.utils = utils;
        this.categoryTransactionService = categoryTransactionService;
        this.currentUser = currentUser;
    }

    public void showAllIncoming() {
        showAll(true);
    }

    public void showAllOutgoing() {
        showAll(false);
    }

    private void showAll(boolean isIncoming) {
        System.out.println("You must enter the date after the transaction");
        LocalDate dateAfter = utils.enterDate();
        System.out.println("You must enter the date before the transaction");
        LocalDate dateBefore = utils.enterDate();
        Map<String, BigDecimal> transactions;
        if (isIncoming) {
            transactions = categoryTransactionService
                    .getIncomingTransactions(currentUser.getCurrentUser().getId(), dateAfter, dateBefore);
            System.out.println("Incoming transactions:");
        } else {
            transactions = categoryTransactionService
                    .getOutgoingTransactions(currentUser.getCurrentUser().getId(), dateAfter, dateBefore);
            System.out.println("Outgoing transactions:");
        }
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            showTransactionResult(transactions);
        }
    }

    private void showTransactionResult(Map<String, BigDecimal> transactions) {
        for (Entry<String, BigDecimal> entry : transactions.entrySet()) {
            System.out.print(entry.getKey() + " - " + entry.getValue() + "; ");
        }
        System.out.println();
    }
}
