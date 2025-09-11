package ru.gnezdilov.api;

import ru.gnezdilov.api.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractController {
    protected Integer extractUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException();
        }
        return userId;
    }

    protected void wrapUserId(HttpServletRequest request, Integer userId) {
        if (userId == null) {
            throw new UnauthorizedException();
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
    }
}
