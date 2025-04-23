package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.DataSourceException;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AuthServlet extends HttpServlet {
    private final WebUtils webUtils;
    private final AuthService authService;

    public AuthServlet() {
        this.webUtils = SpringContext.getContext().getBean(WebUtils.class);
        this.authService = SpringContext.getContext().getBean(AuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            Map<String, String[]> parameterMap = req.getParameterMap();
            if (parameterMap.isEmpty()) {
                printPageInfo(writer);
            } else {
                String[] incomingParameter = new String[] {"email", "password"};
                webUtils.checkParameterMap(parameterMap, incomingParameter);
                String email = webUtils.getEmailFromParameter(parameterMap);
                String password = webUtils.getPasswordFromParameter(parameterMap);
                UserDTO user = authService.authorization(email, password);
                writer.write("<p>Logged in successfully</p>");
                req.getSession().setAttribute("userId", user.getId());
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                 DataSourceException | IllegalArgumentException e) {
            writer.write("<p>" + e.getMessage() + "</p>");

        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer) {
        writer.write("<h1>Authorization</h1>");
        writer.write("<h3>Incoming parameters</h3>");
        writer.write("<ul>" +
                "<li>email</li>" +
                "<li>password</li>" +
                "</ul>");
    }
}
