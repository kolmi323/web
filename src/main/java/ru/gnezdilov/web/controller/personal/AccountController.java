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
    public String account() {
        return "personal/account/main";
    }

    @GetMapping("/show")
    public String showAccount(Model model) {
        Integer userId = this.currentUser().getId();
        List<AccountDTO> accounts = accountService.getAll(userId);
        model.addAttribute("accounts", accounts);
        return "personal/account/show";
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        model.addAttribute("form", new AccountAddForm());
        return "personal/account/add";
    }

    @PostMapping("/add")
    public String addAccount(@ModelAttribute("form") @Valid AccountAddForm form,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        Integer userId = this.currentUser().getId();
        if (!result.hasErrors()) {
            AccountDTO account = accountService.create(userId, form.getName(), form.getBalance());
            return this.redirectMessage("Account " + account.getId() + " - created", redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/account/add";
    }

    @GetMapping("/delete")
    public String deleteAccount(Model model) {
        model.addAttribute("form", new DeleteForm());
        return "personal/account/delete";
    }

    @PostMapping("/delete")
    public String deleteAccount(@ModelAttribute("form") @Valid DeleteForm form,
                             BindingResult result,
                             Model model, RedirectAttributes redirectAttributes) {
        Integer userId = this.currentUser().getId();
        if (!result.hasErrors()) {
            accountService.delete(form.getId(), userId);
            return this.redirectMessage("Account " + form.getId() + " - deleted", redirectAttributes);
        }
        model.addAttribute("form", form);
        return "personal/account/delete";
    }
}
