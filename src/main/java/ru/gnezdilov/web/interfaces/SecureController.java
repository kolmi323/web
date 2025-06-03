package ru.gnezdilov.web.interfaces;

public interface SecureController <REQ, RES> {
    RES handle(REQ request, int userId);

    Class<REQ> getRequestClass();
}
