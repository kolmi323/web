package ru.gnezdilov.web.auth;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AuthServlet extends AbstractServlet {
    private final AuthService authService;

    public AuthServlet() {
        super();
        this.authService = SpringContext.getContext().getBean(AuthService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        String email = this.extractEmail(req);
        String password = this.extractPassword(req);
        this.setUser(this.authService.authorization(email, password));
        writer.write("<p>Logged in successfully</p>");
        req.getSession().setAttribute("userId", this.getUser().getId());
    }
}
