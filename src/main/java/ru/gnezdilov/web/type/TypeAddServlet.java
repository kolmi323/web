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

public class TypeAddServlet extends HttpServlet {
    private final UserService userService;
    private final WebUtils webUtils;
    private final TypeService typeService;

    public TypeAddServlet() {
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
                writer.write("<h1>You are not logged in</h1>");
            } else {
                UserDTO user = userService.getUserById(Integer.parseInt(userId.toString()));
                Map<String, String[]> parameterMap = req.getParameterMap();
                if (parameterMap.isEmpty()) {
                    printPageInfo(writer, user.getName());
                } else {
                    String[] incomingParameter = new String[] {"name"};
                    webUtils.checkParameterMap(parameterMap, incomingParameter);
                    String name = webUtils.getFirstValueFromParameter(parameterMap, "name");
                    TypeDTO type = typeService.create(user.getId(), name);
                    writer.write("<p>Type create:<em>" + type.getName() + "</em></p>");
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException | IllegalArgumentException |
                 DataSourceException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer, String userName) {
        writer.write("<h1><em>" + userName + "</em>, welcome to page for create type</h1>");
        writer.write("<h3>Incoming parameters</h3>");
        writer.write("<ul>" +
                "<li>name</li>" +
                "</ul>");
    }
}
