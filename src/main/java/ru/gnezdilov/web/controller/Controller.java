package ru.gnezdilov.web.controller;

public interface Controller<REQ, RES> {
    RES handle(REQ request);

    Class<REQ> getRequestClass();
}
