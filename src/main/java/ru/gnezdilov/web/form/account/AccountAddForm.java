package ru.gnezdilov.web.form.account;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class AccountAddForm {
    @NotEmpty
    private String name;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Positive
    private BigDecimal balance;
}
