package ru.gnezdilov.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gnezdilov.web.WebController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends WebController {
    @GetMapping("/alertError")
    public String error(Model model, HttpServletRequest request) {
        String error = request.getParameter("errorMessage");
        model.addAttribute("errorMessage", error);
        return "error";
    }
}
