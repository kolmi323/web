package ru.gnezdilov.web.account;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountShowServlet extends HttpServlet {
    private final UserService userService;
    private final AccountService accountService;

    public AccountShowServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
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
                List<AccountDTO> accounts = accountService.getAll(user.getId());
                if (accounts.isEmpty()) {
                    writer.write("<h1><em>" + user.getName() + "</em>, you don't have any accounts.</h1>");
                } else {
                    writer.write("<h1><em>" + user.getName() + "</em>, here are your accounts: </h1>");
                    accounts.forEach(account -> writer.write("<p>" + account.toString() + "</p>"));
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        }  finally {
            writer.close();
        }
    }
}
