package ru.gnezdilov.view.personal.Transaction;

import ru.gnezdilov.dao.exception.ExitException;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;
import ru.gnezdilov.view.personal.Account.AccountActionMenu;
import ru.gnezdilov.view.personal.Type.TypeActionMenu;

import java.math.BigDecimal;

public class TransactionActionMenu {
    private final UIUtils utils;
    private final TransactionService transactionService;
    private final AccountActionMenu accountActionMenu;
    private final TypeActionMenu typeActionMenu;

    public TransactionActionMenu(UIUtils utils, TransactionService transactionService,
                                 AccountActionMenu accountActionMenu, TypeActionMenu typeActionMenu) {
        this.utils = utils;
        this.transactionService = transactionService;
        this.accountActionMenu = accountActionMenu;
        this.typeActionMenu = typeActionMenu;
    }

    public void create() {
        System.out.println("You need to enter the type of creating transaction!");
        typeActionMenu.showAll();
        int typeId = utils.enterId();
        accountActionMenu.showAll();
        System.out.println("You need to enter the id of SENDING transaction!");
        int sendingId = utils.enterId();
        System.out.println("You need to enter the id of RECEIVING transaction!");
        int receivingId = utils.enterId();
        System.out.println("You need to enter the SENDING AMOUNT!");
        BigDecimal amount = utils.enterBalanceAccount();
        if (!isAmountPositive(amount)) {
            System.out.println("You need to enter a positive amount!");
            utils.getExitAction();
        }
        TransactionDTO transaction = this.transactionService.create(typeId, ViewFactory.getCurrentUser().getId(),
                sendingId, receivingId, amount);
        if (transaction == null) {
            System.out.println("Transaction creation failed!");
        } else {
            System.out.println("Transaction\n" +
                    transaction + " - created");
        }
    }

    public boolean isAmountPositive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
