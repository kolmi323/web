package ru.gnezdilov.web.json.account.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountAddResponse implements AbstractResponse {
    private int id;
    private String name;
    private BigDecimal balance;
}
