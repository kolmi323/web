package ru.gnezdilov.web.personal.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class TypeUpdateServlet extends AbstractServlet {
    private final TypeService typeService;

    public TypeUpdateServlet() {
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        int id = this.extractInt(req, "id");
        String newName = this.extractString(req, "newName");
        TypeDTO newType = typeService.edit(id, this.getUser().getId(), newName);
        writer.println("<p>New name: <em>" + newType.getName() + "<em/></p>");
    }
}
