package ru.gnezdilov.web.auth;

import ru.gnezdilov.web.AbstractServletNavigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class StartServlet extends AbstractServletNavigation {
    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.printPageInfo(writer);
    }

    @Override
    protected String getNamePage() {
        return "Start Page";
    }

    @Override
    protected List<String> getNavigationUrl() {
        return List.of("/login", "/register", "/personal");
    }
}

