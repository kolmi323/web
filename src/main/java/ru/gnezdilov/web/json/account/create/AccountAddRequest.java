package ru.gnezdilov.web.json.account.create;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountAddRequest implements AbstractRequest {
    private String name;
    private BigDecimal balance;
}
