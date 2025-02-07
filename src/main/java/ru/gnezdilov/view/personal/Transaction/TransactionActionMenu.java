package ru.gnezdilov.view.personal.Transaction;

import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.view.ViewFactory;

import java.math.BigDecimal;
import java.util.List;

public class TransactionActionMenu {
    private final UIUtils utils;
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionActionMenu(UIUtils utils, TransactionService transactionService, AccountService accountService) {
        this.utils = utils;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    public void showAll() {
        List<TransactionDTO> transactions = this
                .transactionService.getAll(ViewFactory.getCurrentUser().getId());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found");
        } else {
            transactions.stream()
                    .distinct()
                    .forEach(t -> System.out.printf("%d: %s - %s \n from %d to %d\n",
                            t.getId(), t.getDate().toString(), t.getAmount().toString(), t.getSenderAccountId(),
                    t.getReceiverAccountId()));
        }
    }

    private boolean idIsExist(List<AccountDTO> accounts, int id) {
        return accounts.stream().anyMatch(a -> a.getId() == id);
    }

    private String returnAccountId(List<AccountDTO> accounts) {
        while (true) {
            String id = utils.enterId();
            if (utils.isExitAction(id)) {
                return id;
            } else if (idIsExist(accounts, Integer.parseInt(id))) {
                return id;
            } else {
                System.out.println("The account with this ID was not found. Please try again.");
            }
        }
    }

    private String returnSenderAmount(AccountDTO toAccountDTO) {
        String amount;
        BigDecimal bufferAmount;
        while (true) {
            amount = utils.enterBalanceAccount();
            bufferAmount = new BigDecimal(amount);
            if (utils.isExitAction(amount)) {
                return amount;
            } else if (toAccountDTO.getBalance().compareTo(bufferAmount) >= 0 &&
                    bufferAmount.compareTo(BigDecimal.ZERO) >= 0) {
                return amount;
            } else {
                System.out.println("On this account have not enough money. Please try again.");
            }
        }
    }

    private AccountDTO returnAccountDTO(List<AccountDTO> accounts, int fromAccountId) {
        return accounts
                .stream()
                .filter(a -> a.getId() == fromAccountId)
                .findFirst()
                .get();
    }

    public void create() {
        List<AccountDTO> accounts = this.accountService.getAll(ViewFactory.getCurrentUser().getId());
        if (accounts.isEmpty()) {
            System.out.println("You don't have any accounts");
            return;
        } else {
            accounts.forEach(System.out::println);
        }
        System.out.println("Enter id for SENDER account");
        String fromAccountId = returnAccountId(accounts);
        if (utils.isExitAction(fromAccountId)) {
            return;
        }
        AccountDTO fromAccountDTO = returnAccountDTO(accounts, Integer.parseInt(fromAccountId));
        System.out.println("Enter id for RECEIVER account");
        String toAccountId = utils.enterId();
        if (utils.isExitAction(toAccountId)) {
            return;
        }
        System.out.println("Enter AMOUNT for sender account");
        String amount = returnSenderAmount(fromAccountDTO);
        if (utils.isExitAction(amount)) {
            return;
        }
        if (this.transactionService.create(Integer.parseInt(fromAccountId), Integer.parseInt(toAccountId), new BigDecimal(amount)) != 0) {
            System.out.println("Transaction created");
        }
    }
}
