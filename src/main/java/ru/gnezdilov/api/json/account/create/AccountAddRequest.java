package ru.gnezdilov.api.json.account.create;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
public class AccountAddRequest implements AbstractRequest {
    @NotEmpty
    private String name;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Positive
    private BigDecimal balance;
}
