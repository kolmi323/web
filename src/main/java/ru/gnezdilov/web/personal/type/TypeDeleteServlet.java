package ru.gnezdilov.web.personal.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TypeDeleteServlet extends AbstractServlet {
    private final TypeService typeService;

    public TypeDeleteServlet() {
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        int id = this.extractInt(req, "id");
        if (typeService.delete(id, this.getUser().getId())) {
            writer.write("<p>Type:<em> " + id + "</em> - deleted</p>");
        } else {
            writer.write("<p>Type:<em> " + id + "</em> - not found</p>");
        }
    }
}
