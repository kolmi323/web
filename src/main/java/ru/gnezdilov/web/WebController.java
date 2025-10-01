package ru.gnezdilov.web;

import com.fasterxml.jackson.core.JsonParseException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.exception.UnauthorizedException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.InsufficientFundsException;
import ru.gnezdilov.dao.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class WebController extends AbstractController {
    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException() {
        return "redirect:/start";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException e,
                                                     RedirectAttributes redirectAttributes) {
        if (e.getSQLState().equals("23505")) {
            redirectAttributes.addAttribute( "error", "Such a record already exists");
        } else {
            redirectAttributes.addAttribute("error", e.getMessage());
        }
        return "redirect:/alertError";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolationException(DataIntegrityViolationException e, RedirectAttributes redirectAttributes) {
        if (e.getCause() instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) e.getCause(), redirectAttributes);
        } else {
            return redirectException(e, redirectAttributes);
        }
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public String handleInsufficientFundsException(InsufficientFundsException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(NumberFormatException e,
                                                                          RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(JsonParseException.class)
    public String handleJsonParseException(JsonParseException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(DAOException.class)
    public String handleDAOException(DAOException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(DataAccessException e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        return redirectException(e, redirectAttributes);
    }

    protected String pullAttributeFromSession(HttpServletRequest request, String nameAttribute) {
        HttpSession session = request.getSession();
        return session.getAttribute(nameAttribute).toString();
    }

    protected String redirectMessage(String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("message", message);
        return "redirect:/alertMessage";
    }

    private String redirectException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute( "error", e.getMessage());
        return "redirect:/alertError";
    }
}
