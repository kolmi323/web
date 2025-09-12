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
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.account.AccountAddForm;
import ru.gnezdilov.web.form.DeleteForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController extends WebController {
    private final AccountService accountService;

    @GetMapping()
    public String account(HttpServletRequest request) {
        this.extractUserId(request);
        return "personal/account/account_main";
    }

    @GetMapping("/show")
    public String showAccount(HttpServletRequest request,
                              Model model) {
        Integer userId = this.extractUserId(request);
        List<AccountDTO> accounts = accountService.getAll(userId);
        model.addAttribute("accounts", accounts);
        return "personal/account/account_show";
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        model.addAttribute("form", new AccountAddForm());
        model.addAttribute("message", " ");
        return "personal/account/account_add";
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute("form") @Valid AccountAddForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            AccountDTO account = accountService.create(userId, form.getName(), form.getBalance());
            if (account != null) {
                model.addAttribute("message", "Account " + account.getId() + " - created");
            } else {
                model.addAttribute("message", " ");
            }
        }
        model.addAttribute("form", form);
        return "personal/account/account_add";
    }

    @GetMapping("/delete")
    public String deleteAccount(Model model) {
        model.addAttribute("form", new DeleteForm());
        model.addAttribute("message", " ");
        return "personal/account/account_delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(@ModelAttribute("form") @Valid DeleteForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            try {
                if (accountService.delete(form.getId(), userId)) {
                    model.addAttribute("message", "Account " + form.getId() + " - deleted");
                } else {
                    model.addAttribute("message", "Deleted failed");
                }
            } catch (Exception e) {
                result.addError(new FieldError("form", "id", e.getMessage()));
            }
        }
        model.addAttribute("form", form);
        return "personal/account/account_delete";
    }
}
