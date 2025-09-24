package ru.gnezdilov.view.personal.Account;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.view.UIUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class AccountActionMenu {
    private final UIUtils utils;
    private final AccountService accountService;

    public AccountActionMenu(UIUtils utils, AccountService accountService) {
        this.utils = utils;
        this.accountService = accountService;
    }

    public void showAll(UserDTO currentUser) {
        List<AccountDTO> accounts = accountService.getAll(currentUser.getId());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    public void createAccount(UserDTO currentUser) {
        String name = this.utils.enterNameAccount();
        BigDecimal answer = this.utils.enterBalanceAccount();
        BigDecimal balance = answer.setScale(2, RoundingMode.HALF_UP);
        AccountDTO account = accountService
                .create(currentUser.getId(), name, balance);
        System.out.println("New account " + account.getName() + " created");
    }

    public void removeAccount(UserDTO currentUser) {
        int id = this.utils.enterId();
        accountService.delete(id, currentUser.getId());
        System.out.println("Account: " + id + " - deleted");
    }
}
