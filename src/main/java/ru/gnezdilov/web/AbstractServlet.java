package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.DataSourceException;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.web.exception.MissingRequestParameterException;
import ru.gnezdilov.web.exception.UnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class AbstractServlet extends HttpServlet {
    private final UserService userService;
    private final UIUtils utils;
    private UserDTO user;

    protected AbstractServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.utils = SpringContext.getContext().getBean(UIUtils.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        try {
            internalDoGet(req, resp, writer);
        } catch (NotFoundException | AlreadyExistsException | DAOException | MissingRequestParameterException |
                 DataSourceException | IllegalArgumentException | UnauthorizedException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    protected abstract void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer);

    protected void verification(HttpServletRequest req) {
        Object userId = req.getSession().getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException();
        } else {
            this.setUser(userService.getUserById(Integer.parseInt(userId.toString())));
        }
    }

    protected String extractString(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        return value;
    }

    protected int extractInt(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 1234");
        }
    }

    protected BigDecimal extractBigDecimal(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 12345.12");
        }
    }

    protected LocalDate extractLocalDate(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data is not valid" +
                    "\nData example: 2024-12-31");
        }
    }

    protected String extractEmail(HttpServletRequest req) {
        String email = extractString(req, "email");
        if (!utils.isEmailCorrect(email)) {
            throw new IllegalArgumentException("Email is not valid" +
                    "\nEmail example: vasnecov@yandex.ru");
        }
        return email;
    }

    protected String extractPassword(HttpServletRequest req) {
        String password = extractString(req, "password");
        if (!utils.isPasswordCorrect(password)) {
            throw new IllegalArgumentException("Password is too long");
        }
        return password;
    }

    protected UserDTO getUser() {
        return user;
    }

    protected void setUser(UserDTO user) {
        this.user = user;
    }
}
