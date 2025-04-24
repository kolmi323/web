package ru.gnezdilov.web.personal;

import ru.gnezdilov.web.AbstractServletNavigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class PersonalServlet extends AbstractServletNavigation {

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        this.printPageInfo(writer, this.getUser().getName());
    }

    @Override
    protected List<String> getNavigationUrl() {
        return List.of("/account", "/type", "/transaction", "/reports");
    }

    @Override
    protected String getNamePage() {
        return "personal page";
    }
}
