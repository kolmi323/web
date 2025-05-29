package ru.gnezdilov.web.json.account.create;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountAddRequest extends AbstractRequest {
    private String name;
    private BigDecimal balance;
}
