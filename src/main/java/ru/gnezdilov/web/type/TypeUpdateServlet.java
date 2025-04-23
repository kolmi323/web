package ru.gnezdilov.web.type;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

public class TypeUpdateServlet extends HttpServlet {
    private final UserService userService;
    private final WebUtils webUtils;
    private final TypeService typeService;

    public TypeUpdateServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.webUtils = SpringContext.getContext().getBean(WebUtils.class);
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            Object userId = req.getSession().getAttribute("userId");
            if (userId == null) {
                writer.println("<h1>You are not logged in</h1>");
            } else {
                UserDTO user = userService.getUserById(Integer.parseInt(userId.toString()));
                Map<String, String[]> parameterMap = req.getParameterMap();
                if (parameterMap.isEmpty()) {
                    printPageInfo(writer, user.getName());
                } else {
                    String[] incomingParameter = new String[] {"id", "newName"};
                    webUtils.checkParameterMap(parameterMap, incomingParameter);
                    int id = webUtils.getIntFromParameter(parameterMap, "id");
                    String newName = webUtils.getFirstValueFromParameter(parameterMap, "newName");
                    TypeDTO newType = typeService.edit(id, user.getId(), newName);
                    writer.println("<p>New name: <em>" + newType.getName() + "<em/></p>");
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | IllegalArgumentException |
                 DataSourceException e) {
            writer.println("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer, String userName) {
        writer.write("<h1><em>" + userName + "</em>, welcome to page for update type</h1>");
        writer.write("<h3>Incoming parameters</h3>");
        writer.write("<ul>" +
                "<li>id</li>" +
                "<li>newName</li>" +
                "</ul>");
    }
}
