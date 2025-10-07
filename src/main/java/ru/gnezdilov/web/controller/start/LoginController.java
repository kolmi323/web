package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gnezdilov.web.WebController;

@Controller
@RequiredArgsConstructor
public class LoginController extends WebController {
    @GetMapping("/login-form")
    public String login() {
        return "login-form";
    }
}
