package ru.gnezdilov.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gnezdilov.web.WebController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AlertController extends WebController {
    @GetMapping("/alertError")
    public String error(Model model, HttpServletRequest request) {
        String error = request.getParameter("error");
        model.addAttribute("error", error);
        return "error";
    }

    @GetMapping("/alertMessage")
    public String message(Model model, HttpServletRequest request) {
        String message = request.getParameter("message");
        model.addAttribute("message", message);
        return "message";
    }
}
