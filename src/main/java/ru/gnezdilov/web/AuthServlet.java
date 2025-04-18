package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.DataSourceException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class AuthServlet extends HttpServlet {
    private final AuthService authService;
    private final UIUtils utils;

    public AuthServlet() {
        this.authService = SpringContext.getContext().getBean(AuthService.class);
        this.utils = SpringContext.getContext().getBean(UIUtils.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Login</h1>");
            writer.write("<form action=login method=\"post\">");
            writer.write("email: <input name=email>");
            writer.write("<br><br>");
            writer.write("password: <input name=\"password\"/>");
            writer.write("<br><br>");
            writer.write("<input type=\"submit\" value=\"log in\"/></form>");
            writer.write("<form action=\"/start\" method=\"get\">");
            writer.write("<button>exit</button></form>");
            super.service(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/personal");
        PrintWriter writer = resp.getWriter();
        try {
            if (!utils.isEmailCorrect(email)) {
                writer.print("<p>Please enter a valid email address.\nEmail example: vasnecov@yandex.ru</p>");
            } else if (!utils.isPasswordCorrect(password)) {
                writer.print("<p>Password is too long</p>");
            } else {
                UserDTO user = authService.authorization(email, password);
                HttpSession session = req.getSession();
                session.setAttribute("userId", user.getId());
                dispatcher.forward(req, resp);
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException e) {
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
