package ru.gnezdilov.view.personal.CategoryTransaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.view.UIUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CategoryTransactionActionMenu {
    private final UIUtils utils;
    private final CategoryTransactionService categoryTransactionService;

    public CategoryTransactionActionMenu(UIUtils utils, CategoryTransactionService categoryTransactionService) {
        this.utils = utils;
        this.categoryTransactionService = categoryTransactionService;
    }

    public void showAllIncoming(UserDTO currentUser) {
        showAll(currentUser, true);
    }

    public void showAllOutgoing(UserDTO currentUser) {
        showAll(currentUser, false);
    }

    private void showAll(UserDTO currentUser, boolean isIncoming) {
        System.out.println("You must enter the date after the transaction");
        LocalDate dateAfter = utils.enterDate();
        System.out.println("You must enter the date before the transaction");
        LocalDate dateBefore = utils.enterDate();
        Map<String, BigDecimal> transactions;
        if (isIncoming) {
            transactions = categoryTransactionService
                    .getIncomingReport(currentUser.getId(), dateAfter, dateBefore);
            System.out.println("Incoming transactions:");
        } else {
            transactions = categoryTransactionService
                    .getOutgoingReport(currentUser.getId(), dateAfter, dateBefore);
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
