package ru.gnezdilov.web.personal.account;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class AccountShowServlet extends AbstractServlet {
    private final AccountService accountService;

    public AccountShowServlet() {
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        List<AccountDTO> accounts = this.accountService.getAll(this.getUser().getId());
        if (accounts.isEmpty()) {
            writer.write("<h1><em>" + this.getUser().getName() + "</em>, you don't have any accounts.</h1>");
        } else {
            writer.write("<h1><em>" + this.getUser().getName() + "</em>, here are your accounts: </h1>");
            accounts.forEach(account -> writer.write("<p>" + account.toString() + "</p>"));
        }
    }
}
