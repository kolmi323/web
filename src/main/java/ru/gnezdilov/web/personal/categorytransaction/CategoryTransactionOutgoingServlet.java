package ru.gnezdilov.web.personal.categorytransaction;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.AbstractServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CategoryTransactionOutgoingServlet extends AbstractServlet {
    private final CategoryTransactionService categoryTransactionService;

    public CategoryTransactionOutgoingServlet() {
        this.categoryTransactionService = SpringContext.getContext().getBean(CategoryTransactionService.class);
    }

    @Override
    protected void internalDoGet(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer) {
        this.verification(req);
        LocalDate dateAfter = this.extractLocalDate(req, "dateAfter");
        LocalDate dateBefore = this.extractLocalDate(req, "dateBefore");
        Map<String, BigDecimal> transactions = categoryTransactionService
                .getOutgoingTransactions(this.getUser().getId(), dateAfter, dateBefore);
        writer.write("<p>Outgoing transaction:</p>");
        transactions.entrySet()
                .forEach(entry -> {
                    writer.write("<p>"+ entry.getKey() + " - " + entry.getValue() + "; </p>");
                });
    }
}
