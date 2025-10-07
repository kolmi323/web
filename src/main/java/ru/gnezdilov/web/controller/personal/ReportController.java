package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.report.ReportDataForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController extends WebController {
    private final String NAME_INCOMING = "incoming";
    private final String NAME_OUTGOING = "outgoing";
    private final CategoryTransactionService categoryTransactionService;

    @GetMapping()
    public String report() {
        return "personal/report/main";
    }

    @GetMapping("/incoming")
    public String reportIncomingData(Model model) {
        return reportData(model, NAME_INCOMING);
    }

    @PostMapping("/incoming")
    private String reportIncomingData(@ModelAttribute("form") @Valid ReportDataForm form,
                              BindingResult result,
                              Model model,
                              HttpServletRequest request) {
        return reportData(form, result, model, request, NAME_INCOMING);
    }

    @GetMapping("/outgoing")
    public String reportOutgoingData(Model model) {
        return reportData(model, NAME_OUTGOING);
    }

    @PostMapping("/outgoing")
    private String reportOutgoingData(@ModelAttribute("form") @Valid ReportDataForm form,
                                      BindingResult result,
                                      Model model,
                                      HttpServletRequest request) {
        return reportData(form, result, model, request, NAME_OUTGOING);
    }

    @GetMapping("/show")
    public String showReport(Model model,
                              HttpServletRequest request) {
        Integer userId = this.currentUser().getId();
        Map<String, BigDecimal> transactions = new HashMap<>();
        String typeReport = this.pullAttributeFromSession(request, "typeReport");
        if (typeReport.equals(NAME_INCOMING)) {
            transactions = categoryTransactionService.getIncomingTransactions(userId,
                    LocalDate.parse(this.pullAttributeFromSession(request, "dateAfter")),
                    LocalDate.parse(this.pullAttributeFromSession(request, "dateBefore")));
        } else if (typeReport.equals(NAME_OUTGOING)) {
            transactions = categoryTransactionService.getOutgoingTransactions(userId,
                    LocalDate.parse(this.pullAttributeFromSession(request, "dateAfter")),
                    LocalDate.parse(this.pullAttributeFromSession(request, "dateBefore")));
        }
        model.addAttribute("transactions", transactions);
        model.addAttribute("type_report", typeReport);
        return "personal/report/show";
    }

    private String reportData(Model model, String typeReport) {
        model.addAttribute("form", new ReportDataForm());
        model.addAttribute("type_report", typeReport);
        return "personal/report/data";
    }

    private String reportData(ReportDataForm form,
                              BindingResult result,
                              Model model,
                              HttpServletRequest request,
                              String typeReport) {
        if (!result.hasErrors()) {
            HttpSession session = request.getSession();
            session.setAttribute("dateAfter", form.getDateAfter());
            session.setAttribute("dateBefore", form.getDateBefore());
            session.setAttribute("typeReport", typeReport);
            return "redirect:/report/show";
        }
        model.addAttribute("form", form);
        model.addAttribute("type_report", typeReport);
        return "personal/report/data";
    }
}
