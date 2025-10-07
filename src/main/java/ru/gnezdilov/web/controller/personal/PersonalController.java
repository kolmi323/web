package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gnezdilov.security.CustomUserDetails;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.WebController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class PersonalController extends WebController {
    private final UserService userService;

    @GetMapping("/personal")
    public String index(Model model) {
        UserDTO user = userService.getUserById(currentUser().getId());
        model.addAttribute("name", user.getName());
        return "personal/menu";
    }
}
