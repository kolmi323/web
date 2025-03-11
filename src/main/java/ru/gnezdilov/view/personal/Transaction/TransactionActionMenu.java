package ru.gnezdilov.view.personal.Transaction;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.personal.Account.AccountActionMenu;
import ru.gnezdilov.view.personal.Type.TypeActionMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
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

    public void create(UserDTO currentUser) {
        List<Integer> typeIds = returnListTypeIds(currentUser);
        accountActionMenu.showAll(currentUser);
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
        TransactionDTO transaction = this.transactionService.create(typeIds, currentUser.getId(),
                sendingId, receivingId, amount);
        if (transaction == null) {
            System.out.println("Transaction creation failed!");
        } else {
            System.out.println("Transaction\n" +
                    transaction + " - created");
        }
    }

    private boolean isAmountPositive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    private List<Integer> returnListTypeIds(UserDTO currentUser) {
        List<Integer> typeIds = new ArrayList<>();
        typeActionMenu.showAll(currentUser);
        System.out.println("You need to enter the type ids of creating transaction!" +
                "\nif you enter enough ids - press 0");
        while(true) {
            int typeId = utils.enterId();
            if (typeId == 0) {
                break;
            }
            typeIds.add(typeId);
        }
        return typeIds;
    }
}
