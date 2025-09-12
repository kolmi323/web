package ru.gnezdilov.web;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class WebController extends AbstractController {
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException() {
        return "redirect:/start";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e, RedirectAttributes redirectAttributes) {
        if (e.getSQLState().equals("23505")) {
            redirectAttributes.addAttribute( "errorMessage", "Such a record already exists");
        } else {
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/alertError";
    }

    protected void removeAttributeFromSession(HttpServletRequest request, String nameAttribute) {
        HttpSession session = request.getSession();
        session.removeAttribute(nameAttribute);
    }
}
