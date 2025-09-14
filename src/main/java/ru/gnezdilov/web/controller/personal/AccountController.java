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
        return "personal/account/accountMain";
    }

    @GetMapping("/show")
    public String showAccount(HttpServletRequest request,
                              Model model) {
        Integer userId = this.extractUserId(request);
        List<AccountDTO> accounts = accountService.getAll(userId);
        model.addAttribute("accounts", accounts);
        return "personal/account/accountShow";
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        model.addAttribute("form", new AccountAddForm());
        return "personal/account/accountAdd";
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute("form") @Valid AccountAddForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            AccountDTO account = accountService.create(userId, form.getName(), form.getBalance());
            return this.handleMessage("Account " + account.getId() + " - created", redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/account/accountAdd";
    }

    @GetMapping("/delete")
    public String deleteAccount(Model model) {
        model.addAttribute("form", new DeleteForm());
        return "personal/account/accountDelete";
    }

    @PostMapping("/delete")
    public String deleteAccount(@ModelAttribute("form") @Valid DeleteForm form,
                             BindingResult result,
                             Model model,
                             HttpServletRequest request,
                                RedirectAttributes redirectAttributes) {
        Integer userId = this.extractUserId(request);
        if (!result.hasErrors()) {
            if (accountService.delete(form.getId(), userId)) {
                return this.handleMessage("Account " + form.getId() + " - deleted", redirectAttributes);
            } else {
                return this.handleMessage("Deleted failed", redirectAttributes);
            }
        }
        model.addAttribute("form", form);
        return "personal/account/accountDelete";
    }
}
