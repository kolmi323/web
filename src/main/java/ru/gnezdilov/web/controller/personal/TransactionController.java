package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.transaction.TransactionAddForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController extends WebController {
    private final TransactionService transactionService;

    @GetMapping()
    public String transaction(HttpServletRequest request) {
        this.extractUserId(request);
        return "personal/transaction/transaction_main";
    }

    @GetMapping("/add")
    public String addTransaction(Model model) {
        model.addAttribute("form", new TransactionAddForm());
        model.addAttribute("message", " ");
        return "personal/transaction/transaction_add";
    }

    @PostMapping("/add")
    public String addTransaction(@ModelAttribute("form") @Valid TransactionAddForm form,
                                 BindingResult result,
                                 Model model,
                                 HttpServletRequest request) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            try {
                List<Integer> typeIds = getIds(form.getTypeIds());
                TransactionDTO transactionDTO = transactionService.create(typeIds,
                        userId,
                        form.getSendingId(),
                        form.getReceivingId(),
                        form.getAmount());
                if (transactionDTO != null) {
                    model.addAttribute("message", "Transaction " + transactionDTO.getId() + " - created.");
                }
            } catch (Exception e) {
                result.addError(new FieldError("form", "amount", e.getMessage()));
            }
        }
        model.addAttribute("form", form);
        return "personal/transaction/transaction_add";
    }

    private List<Integer> getIds(String ids) {
        return ids.isEmpty() ? new ArrayList<>() : Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
