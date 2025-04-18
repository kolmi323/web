package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/personal")
public class PersonalOfficeServlet extends HttpServlet {
    private final UserService userService;
    private UserDTO user;

    public PersonalOfficeServlet() {
        userService = SpringContext.getContext().getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = session.getAttribute("userId") == null ? 0 : (Integer) session.getAttribute("userId");
        PrintWriter writer = resp.getWriter();
        try {
            if (userId == 0) {
                writer.write("<h1>You are not logged in</h1>");
            } else {
                user = userService.getUserById(userId);
                writer.write("<h1>PersonalOffice</h1>");
                writer.write("Welcome to personal office, " + user.getName() + "!");
                writer.write("<form action=\"/start\" method=\"get\">");
                writer.write("<button>exit</button></form>");
            }
        } catch (NotFoundException e) {
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
