package ru.gnezdilov.web.transaction;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionAddServlet extends HttpServlet {
    private final UserService userService;
    private final WebUtils webUtils;
    private final TransactionService transactionService;

    public TransactionAddServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.webUtils = SpringContext.getContext().getBean(WebUtils.class);
        this.transactionService = SpringContext.getContext().getBean(TransactionService.class);
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
                    String[] incomingParameter = new String[] {"typeIds", "sendingId", "receivingId", "amount"};
                    webUtils.checkParameterMap(parameterMap, incomingParameter);
                    List<Integer> typeIds = Arrays
                            .stream(webUtils.getAllValueFromParameter(parameterMap, "typeIds"))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    int sendingId = webUtils.getIntFromParameter(parameterMap, "sendingId");
                    int receivingId = webUtils.getIntFromParameter(parameterMap, "receivingId");
                    BigDecimal amount = webUtils.getBigDecimalFromParameter(parameterMap, "amount");
                    if (amount.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException("You need to enter a positive amount!");
                    }
                    TransactionDTO transaction = transactionService.create(typeIds, user.getId(), sendingId, receivingId, amount);
                    if (transaction == null) {
                        writer.write("<p>Transaction creation failed!</p>");
                    } else {
                        writer.write("<p>Transaction <em>" + transaction + "</em> - created</p>");
                    }
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                DataSourceException | InsufficientFundsException | IllegalArgumentException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer, String userName) {
        writer.write("<h1><em>" + userName + "</em>, welcome to page for create transaction</h1>");
        writer.write("<h3>Incoming parameters</h3>");
        writer.write("<ul>" +
                "<li>typeIds</li>" +
                "<li>sendingId</li>" +
                "<li>receivingId</li>" +
                "<li>amount</li>" +
                "</ul>");
    }
}
