package ru.gnezdilov.web.personal.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class TypeShowServlet extends AbstractServlet {
    private final TypeService typeService;

    public TypeShowServlet() {
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        List<TypeDTO> types = typeService.getAll(this.getUser().getId());
        if (types.isEmpty()) {
            writer.write("<h1><em>" + this.getUser().getName() + "</em>, you don't have any types.</h1>");
        } else {
            writer.write("<h1><em>" + this.getUser().getName() + "</em>, here are your types: </h1>");
            types.forEach(type -> writer.write("<p>" + type.toString() + "</p>"));
        }
    }
}
