package ru.gnezdilov.web.account;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.DataSourceException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AccountServlet extends HttpServlet {
    private final UserService userService;

    public AccountServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
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
                printPageInfo(writer, user.getName());
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                 DataSourceException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        }  finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer, String userName) {
        writer.write("<h1>Welcome to account action menu <em>" + userName + "</em>!</h1>");
        writer.write("<h3>action:</h3>");
        writer.write("<ul>" +
                "<li>/show</li>" +
                "<li>/add</li>" +
                "<li>/delete</li>" +
                "</ul>");
    }
}
