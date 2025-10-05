package ru.gnezdilov.api;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.gnezdilov.security.CustomUserDetails;

public abstract class AbstractController {
    protected CustomUserDetails currentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
