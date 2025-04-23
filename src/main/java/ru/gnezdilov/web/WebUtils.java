package ru.gnezdilov.web;

import org.springframework.stereotype.Component;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.view.UIUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@Component
public class WebUtils {
    private final UIUtils uiUtils;

    public WebUtils(UIUtils uiUtils) {
        this.uiUtils = uiUtils;
    }

    public void checkParameterMap(Map<String, String[]> parameterMap, String[] incomingParameters) {
        parameterMap.keySet()
                .forEach(key -> {
                    if (Arrays.stream(incomingParameters).noneMatch(param -> param.equals(key))) {
                        throw new IllegalArgumentException("<em>" + key + "</em> - wrong parameter");
                    }
                });
    }

    public String[] getAllValueFromParameter(Map<String, String[]> parameterMap, String key) {
        String[] name = parameterMap.get(key);
        if (name == null || Arrays.stream(name).allMatch(String::isEmpty)) {
            throw new IllegalArgumentException(key + " cannot be empty");
        }
        return name;
    }

    public String getFirstValueFromParameter(Map<String, String[]> parameterMap, String key) {
        String name = parameterMap.get(key)[0];
        if (name.isEmpty()) {
            throw new IllegalArgumentException(key + " cannot be empty");
        }
        return name;
    }

    public BigDecimal getBigDecimalFromParameter(Map<String, String[]> parameterMap, String key) {
        String balance = getFirstValueFromParameter(parameterMap, "balance");
        if (!uiUtils.isBalanceAccountCorrect(balance)) {
            throw new IllegalArgumentException("Number in not valid" +
                    "\nBalance example: 12345.12");
        }
        return new BigDecimal(balance);
    }

    public int getIntFromParameter(Map<String, String[]> parameterMap, String key) {
        String id = getFirstValueFromParameter(parameterMap, key);
        return Integer.parseInt(id);
    }

    public LocalDate getLocalDateFromParameter(Map<String, String[]> parameterMap, String key) {
        String date = getFirstValueFromParameter(parameterMap, key);
        if (!uiUtils.isDateCorrect(date)) {
            throw new IllegalArgumentException("Data is not valid" +
                    "\nData example: 2024-12-31");
        }
        return LocalDate.parse(date);
    }

    public boolean getBooleanFromParameter(Map<String, String[]> parameterMap, String key) {
        String bool = getFirstValueFromParameter(parameterMap, key);
        if (bool.equals("true") || bool.equals("false")) {
            return Boolean.parseBoolean(bool);
        }
        throw new IllegalArgumentException(key + " is not boolean value");
    }

    public String getEmailFromParameter(Map<String, String[]> parameterMap) {
        String email = getFirstValueFromParameter(parameterMap, "email");
        if (!uiUtils.isEmailCorrect(email)) {
            throw new IllegalArgumentException("Email is not valid" +
                    "\nEmail example: vasnecov@yandex.ru");
        }
        return email;
    }

    public String getPasswordFromParameter(Map<String, String[]> parameterMap) {
        String password = getFirstValueFromParameter(parameterMap, "password");
        if (!uiUtils.isPasswordCorrect(password)) {
            throw new IllegalArgumentException("Password is too long");
        }
        return password;
    }
}
