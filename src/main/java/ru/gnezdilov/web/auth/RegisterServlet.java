package ru.gnezdilov.web.auth;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class RegisterServlet extends AbstractServlet {
    private final AuthService authService;

    public RegisterServlet() {
        this.authService = SpringContext.getContext().getBean(AuthService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        String name = this.extractString(req, "name");
        String email = this.extractEmail(req);
        String password = this.extractPassword(req);
        this.setUser(this.authService.createNewUser(name, email, password));
        writer.write("<p>User created</p>");
        req.getSession().setAttribute("userId", this.getUser().getId());
    }
}
