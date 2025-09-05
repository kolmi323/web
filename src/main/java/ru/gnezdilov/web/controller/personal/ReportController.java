package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.WebController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController extends WebController {
    private final CategoryTransactionService categoryTransactionService;

    @GetMapping()
    public String report(HttpServletRequest request) {
        Integer userId = this.pullUserIdFromSession(request);
        if (userId == null) {
            return "redirect:/start";
        }
        return "report";
    }
}
