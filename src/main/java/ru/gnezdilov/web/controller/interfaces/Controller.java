package ru.gnezdilov.web.controller.interfaces;

public interface Controller<REQ, RES> {
    RES handle(REQ request);

    Class<REQ> getRequestClass();
}
