package ru.gnezdilov.view.personal.Account;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.view.CurrentUser;
import ru.gnezdilov.view.UIUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class AccountActionMenu {
    private final UIUtils utils;
    private final AccountService accountService;
    private final CurrentUser currentUser;

    public AccountActionMenu(UIUtils utils, AccountService accountService, CurrentUser currentUser) {
        this.utils = utils;
        this.accountService = accountService;
        this.currentUser = currentUser;
    }

    public void showAll() {
        List<AccountDTO> accounts = accountService.getAll(currentUser.getCurrentUser().getId());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    public void createAccount() {
        String name = this.utils.enterNameAccount();
        BigDecimal answer = this.utils.enterBalanceAccount();
        BigDecimal balance = answer.setScale(2, RoundingMode.HALF_UP);
        AccountDTO account = accountService
                .create(currentUser.getCurrentUser().getId(), name, balance);
        System.out.println("New account " + account.getName() + " created");
    }

    public void removeAccount() {
        int id = this.utils.enterId();
        if (accountService.delete(id, currentUser.getCurrentUser().getId())) {
            System.out.println("Account: " + id + " - deleted");
        } else {
            System.out.println("Account: " + id + " - not found");
        }
    }
}
