package ru.gnezdilov.api;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.gnezdilov.api.exception.UnauthorizedException;
import ru.gnezdilov.security.CustomUserDetails;
import ru.gnezdilov.service.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractController {
    protected CustomUserDetails currentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    protected void handleUser(UserDTO user) {
        if (user == null) {
            throw new UnauthorizedException();
        }
    }
}
