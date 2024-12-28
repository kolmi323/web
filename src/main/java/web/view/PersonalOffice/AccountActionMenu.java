package web.view.PersonalOffice;

import web.service.DTO.AccountDTO;
import web.service.ServiceFactory;
import web.view.UIUtils;
import web.view.ViewFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AccountActionMenu {
    private final UIUtils utils;

    public AccountActionMenu() {
        this.utils = new UIUtils();
    }

    public void showListAccount() {
        List<AccountDTO> accounts = ServiceFactory.getAccountAction().getAll(ViewFactory.getCurrentUser().getId());
        if (accounts.isEmpty()) {
            System.out.println("No accounts found");
        } else {
            accounts.forEach(System.out::println);
        }
    }

    public void createAccount() {
        String name = this.utils.enterNameAccount();
        if (this.utils.isExitAction(name)) {
            return;
        }
        String answer = this.utils.enterBalanceAccount();
        if (this.utils.isExitAction(answer)) {
            return;
        }
        BigDecimal balance = new BigDecimal(answer).setScale(2, RoundingMode.HALF_UP);
        AccountDTO account = ServiceFactory.getAccountAction()
                .create(ViewFactory.getCurrentUser().getId(), name, balance);
        System.out.println("New account " + account.getName() + " created");
    }

    public void removeAccount() {
        String name = this.utils.enterNameAccount();
        if (this.utils.isExitAction(name)) {
            return;
        }
        if (ServiceFactory.getAccountAction().delete(ViewFactory.getCurrentUser().getId(), name)) {
            System.out.println("Account: " + name + " - deleted");
        } else {
            System.out.println("Account: " + name + " - not found");
        }
    }
}
