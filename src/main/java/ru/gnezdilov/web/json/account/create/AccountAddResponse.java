package ru.gnezdilov.web.json.account.create;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountAddResponse {
    private int id;
    private String name;
    private BigDecimal balance;
}
