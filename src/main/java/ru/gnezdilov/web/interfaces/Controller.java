package ru.gnezdilov.web.interfaces;

public interface Controller<REQ, RES> {
    RES handle(REQ request);

    Class<REQ> getRequestClass();
}
