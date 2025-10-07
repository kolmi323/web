package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.WebController;
import ru.gnezdilov.web.form.RegisterForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController extends WebController {
    private final AuthService authService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") @Valid RegisterForm form,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (!result.hasErrors()) {
            UserDTO user = authService.createNewUser(form.getName(), form.getEmail(), form.getPassword());
            if (user != null) {
                return this.redirectMessage("User " + user.getName() + " created", redirectAttributes);
            }
        }
        model.addAttribute("form", form);
        return "register";
    }
}
