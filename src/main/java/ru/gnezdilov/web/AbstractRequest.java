package ru.gnezdilov.web;

import ru.gnezdilov.SpringContext;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.web.exception.MissingRequestParameterException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public abstract class AbstractRequest {
    private final UIUtils utils;

    protected AbstractRequest() {
        this.utils = SpringContext.getContext().getBean(UIUtils.class);
    }

    protected String extractString(String paramName, String value) {
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        return value;
    }

    protected int extractInt(String paramName, String value) {
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 1234");
        }
    }

    protected BigDecimal extractBigDecimal(String paramName, String value) {
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 12345.12");
        }
    }

    protected LocalDate extractLocalDate(String paramName, String value) {
        if (value.isEmpty()) {
            throw new MissingRequestParameterException(paramName);
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data is not valid" +
                    "\nData example: 2024-12-31");
        }
    }

    protected String extractEmail(String email) {
        String value = extractString("email", email);
        if (!utils.isEmailCorrect(value)) {
            throw new IllegalArgumentException("Email is not valid" +
                    "\nEmail example: vasnecov@yandex.ru");
        }
        return email;
    }

    protected String extractPassword(String password) {
        String value = extractString("password", password);
        if (!utils.isPasswordCorrect(value)) {
            throw new IllegalArgumentException("Password is too long");
        }
        return password;
    }
}
