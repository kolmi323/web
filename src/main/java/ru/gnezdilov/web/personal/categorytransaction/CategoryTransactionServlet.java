package ru.gnezdilov.web.personal.categorytransaction;

import ru.gnezdilov.web.AbstractServletNavigation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class CategoryTransactionServlet extends AbstractServletNavigation {
    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        this.printPageInfo(writer, this.getUser().getName());
    }

    @Override
    protected String getNamePage() {
        return "report transaction";
    }

    @Override
    protected List<String> getNavigationUrl() {
        return List.of("/incoming", "/outgoing");
    }
}
