package ru.gnezdilov.web.json.account.create;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountAddRequest {
    private String name;
    private BigDecimal balance;
}
