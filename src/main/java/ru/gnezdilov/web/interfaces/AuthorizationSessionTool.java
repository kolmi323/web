package ru.gnezdilov.web.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AuthorizationSessionTool {
    default Integer getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Integer) session.getAttribute("userId");
    }
}
