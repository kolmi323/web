package ru.gnezdilov.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class WebController extends AbstractController {
    protected void removeAttributeUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException() {
        return "redirect:/start";
    }
}
