package ru.gnezdilov.web.personal.transaction;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionAddServlet extends AbstractServlet {
    private final TransactionService transactionService;

    public TransactionAddServlet() {
        this.transactionService = SpringContext.getContext().getBean(TransactionService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        List<Integer> typeIds = getTypeIds(req);
        int sendingId = this.extractInt(req, "sendingId");
        int receivingId = this.extractInt(req, "receivingId");
        BigDecimal amount = getAmount(req);
        TransactionDTO transaction = transactionService.create(typeIds, this.getUser().getId(), sendingId, receivingId, amount);
        if (transaction == null) {
            writer.write("<p>Transaction creation failed!</p>");
        } else {
            writer.write("<p>Transaction <em>" + transaction + "</em> - created</p>");
        }
    }

    private List<Integer> getTypeIds(HttpServletRequest req) {
        String typeIds = this.extractString(req, "typeIds");
        try {
            return Arrays.stream(typeIds.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 1234");
        }
    }

    private BigDecimal getAmount(HttpServletRequest req) {
        BigDecimal amount = this.extractBigDecimal(req, "amount");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("You need to enter a positive amount!");
        }
        return amount;
    }
}
