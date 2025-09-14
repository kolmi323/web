package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.WebController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class PersonalController extends WebController {
    private final UserService userService;

    @GetMapping("/personal")
    public String index(Model model, HttpServletRequest request) {
        Integer userId = this.extractUserId(request);
        UserDTO user = userService.getUserById(userId);
        model.addAttribute("name", user.getName());
        return "personal/personalMenu";
    }
}
