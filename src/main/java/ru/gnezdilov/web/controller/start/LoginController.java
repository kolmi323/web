package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.LoginForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController extends WebController {
    private final AuthService authService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("form") @Valid LoginForm form,
                        BindingResult result,
                        Model model,
                        HttpServletRequest request) {
        if (!result.hasErrors()) {
            UserDTO user = authService.authorization(form.getEmail(), form.getPassword());
            if (user != null) {
                this.wrapUserId(request, user.getId());
                return "redirect:/personal";
            }
        }
        model.addAttribute("form", form);
        return "login";
    }
}
