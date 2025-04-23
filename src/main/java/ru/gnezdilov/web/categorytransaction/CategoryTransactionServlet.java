package ru.gnezdilov.web.categorytransaction;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.*;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CategoryTransactionServlet extends HttpServlet {
    private final UserService userService;
    private final WebUtils webUtils;
    private final CategoryTransactionService categoryTransactionService;

    public CategoryTransactionServlet() {
        this.userService = SpringContext.getContext().getBean(UserService.class);
        this.webUtils = SpringContext.getContext().getBean(WebUtils.class);
        this.categoryTransactionService = SpringContext.getContext().getBean(CategoryTransactionService.class);
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
                    String[] incomingParameter = new String[] {"isIncoming", "dateAfter", "dateBefore"};
                    webUtils.checkParameterMap(parameterMap, incomingParameter);
                    boolean isIncoming = webUtils.getBooleanFromParameter(parameterMap, "isIncoming");
                    LocalDate dateAfter = webUtils.getLocalDateFromParameter(parameterMap, "dateAfter");
                    LocalDate dateBefore = webUtils.getLocalDateFromParameter(parameterMap, "dateBefore");
                    Map<String, BigDecimal> transactions;
                    if (isIncoming) {
                        transactions = categoryTransactionService.getIncomingTransactions(user.getId(), dateAfter, dateBefore);
                        writer.write("<p>Incoming transaction:</p>");
                    } else {
                        transactions = categoryTransactionService.getOutgoingTransactions(user.getId(), dateAfter, dateBefore);
                        writer.write("<p>Outgoing transaction:</p>");
                    }
                    transactions.entrySet()
                            .forEach(entry -> {
                                writer.write("<p>"+ entry.getKey() + " - " + entry.getValue() + "; </p>");
                            });
                }
            }
        } catch (NotFoundException | AlreadyExistsException | DAOException | NullPointerException |
                 IllegalArgumentException | DataSourceException e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } catch (Exception e) {
            writer.write("<p>" + e.getMessage() + "</p>");
        } finally {
            writer.close();
        }
    }

    private void printPageInfo(PrintWriter writer, String userName) {
        writer.write("<h1><em>" + userName + "</em>, welcome to page for report transactions</h1>");
        writer.write("<h3>Incoming parameters</h3>");
        writer.write("<ul>" +
                "<li>isIncoming</li>" +
                "<li>dateAfter</li>" +
                "<li>dateBefore</li>" +
                "</ul>");
    }
}
