package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        return "personal/transaction/transactionMain";
    }

    @GetMapping("/add")
    public String addTransaction(Model model) {
        model.addAttribute("form", new TransactionAddForm());
        return "personal/transaction/transactionAdd";
    }

    @PostMapping("/add")
    public String addTransaction(@ModelAttribute("form") @Valid TransactionAddForm form,
                                 BindingResult result,
                                 Model model,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            List<Integer> typeIds = processIdsFromString(form.getTypeIds());
            TransactionDTO transactionDTO = transactionService.create(typeIds,
                    userId,
                    form.getSendingId(),
                    form.getReceivingId(),
                    form.getAmount());
            return this.handleMessage("Transaction " + transactionDTO.getId() + " - created.", redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/transaction/transactionAdd";
    }

    private List<Integer> processIdsFromString(String ids) {
        return ids.isEmpty() ? new ArrayList<>() : Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
