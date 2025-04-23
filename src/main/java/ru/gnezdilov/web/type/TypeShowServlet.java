package ru.gnezdilov.web.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.service.personal.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TypeShowServlet extends HttpServlet {
    private final UserService userService;
    private final TypeService typeService;

    public TypeShowServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        try {
            Object userId = req.getSession().getAttribute("userId");
            if (userId == null) {
                writer.write("<h1>You are not logged in</h1>");
            } else {
                UserDTO user = userService.getUserById(Integer.parseInt(userId.toString()));
                List<TypeDTO> types = typeService.getAll(user.getId());
                if (types.isEmpty()) {
                    writer.write("<h1><em>" + user.getName() + "</em>, you don't have any types.</h1>");
                } else {
                    writer.write("<h1><em>" + user.getName() + "</em>, here are your types: </h1>");
                    types.forEach(type -> writer.write("<p>" + type.toString() + "</p>"));
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }
}
