package ru.gnezdilov.web.json.account.create;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

import java.math.BigDecimal;

@Getter
public class AccountAddRequest extends AbstractRequest {
    private String name;
    private BigDecimal balance;

    public void setName(String name) {
        this.name = this.extractString("name", name);
    }

    public void setBalance(String balance) {
        this.balance = this.extractBigDecimal("balance", balance.toString());
    }
}
