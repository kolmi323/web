package ru.gnezdilov.web.personal.account;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AccountDeleteServlet extends AbstractServlet {
    private final AccountService accountService;

    public AccountDeleteServlet() {
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        int id = this.extractInt(req, "id");
        if (this.accountService.delete(id, this.getUser().getId())) {
            writer.write("<p>Account:<em> " + id + "</em> - deleted</p>");
        } else {
            writer.write("<p>Account:<em> " + id + "</em> - not found</p>");
        }
    }
}
