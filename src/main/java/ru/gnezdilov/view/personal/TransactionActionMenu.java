package ru.gnezdilov.view.personal;

import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.view.UIUtils;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class TransactionActionMenu {
    private final UIUtils utils;
    private final TransactionService transactionService;
    private final TypeActionMenu typeActionMenu;
    private final AccountActionMenu accountActionMenu;

    public TransactionActionMenu(UIUtils utils, TransactionService transactionService,
                                 TypeActionMenu typeActionMenu, AccountActionMenu accountActionMenu) {
        this.utils = utils;
        this.transactionService = transactionService;
        this.typeActionMenu = typeActionMenu;
        this.accountActionMenu = accountActionMenu;
    }

    public void showAll() {
        typeActionMenu.showAll();
        System.out.println("You must enter the transaction type id");
        String typeId = utils.enterId();
        if (utils.isExitAction(typeId)) {
            return;
        }
        accountActionMenu.showAll();
        System.out.println("You must enter the transaction account id");
        String accountId = utils.enterId();
        if (utils.isExitAction(accountId)) {
            return;
        }
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
        List<TransactionDTO> transactions = transactionService.getAll(Integer.parseInt(typeId),
                Integer.parseInt(accountId), Date.valueOf(dateAfter), Date.valueOf(dateBefore));
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            showTransactionResult(transactions, Integer.parseInt(accountId));
        }
    }

    private void showTransactionResult(List<TransactionDTO> transactions, int accountId) {
        BigDecimal sum = new BigDecimal(0);
        for (TransactionDTO transaction : transactions) {
            if (transactionIsPositive(transaction, accountId)) {
                sum = sum.add(transaction.getAmount());
                System.out.println(transaction + ": +" + transaction.getAmount());
            } else {
                sum = sum.subtract(transaction.getAmount());
                System.out.println(transaction + ": -" + transaction.getAmount());
            }
        }
        System.out.println("Result: " + sum);
    }

    private boolean transactionIsPositive(TransactionDTO transaction, int accountId) {
        return transaction.getReceiverAccountId() == accountId;
    }
}
