package ru.gnezdilov.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractController {
    protected Integer pullUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute("userId");
    }

    protected void putUserIdToSession(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
    }
}
