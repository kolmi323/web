package ru.gnezdilov.web.personal.account;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class AccountAddServlet extends AbstractServlet {
    private final AccountService accountService;

    public AccountAddServlet() {
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        String name = this.extractString(req, "name");
        BigDecimal balance = this.extractBigDecimal(req, "balance");
        AccountDTO account = this.accountService.create(this.getUser().getId(), name, balance);
        writer.write("<p>New account <em>" + account.getName() + "</em> created</p>");
    }
}
