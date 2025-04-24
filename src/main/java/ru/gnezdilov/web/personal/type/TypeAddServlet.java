package ru.gnezdilov.web.personal.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TypeAddServlet extends AbstractServlet {
    private final TypeService typeService;

    public TypeAddServlet() {
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        String name = this.extractString(req, "name");
        TypeDTO type = typeService.create(this.getUser().getId(), name);
        writer.write("<p>Type create:<em>" + type.getName() + "</em></p>");
    }
}
