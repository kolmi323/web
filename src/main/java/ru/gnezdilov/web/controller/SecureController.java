package ru.gnezdilov.web.controller;

public interface SecureController <REQ, RES> {
    RES handle(REQ request, int userId);

    Class<REQ> getRequestClass();
}
