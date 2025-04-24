package ru.gnezdilov.web.personal.type;

import ru.gnezdilov.web.AbstractServletNavigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class TypeServlet extends AbstractServletNavigation {
    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        this.printPageInfo(writer, this.getUser().getName());
    }

    @Override
    protected List<String> getNavigationUrl() {
        return List.of("/show", "/update", "/add", "/delete");
    }

    @Override
    protected String getNamePage() {
        return "type menu";
    }
}
