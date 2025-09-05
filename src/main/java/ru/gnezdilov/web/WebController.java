package ru.gnezdilov.web;

import ru.gnezdilov.api.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebController extends AbstractController {
    protected void removeAttributeUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
    }
}
