package ru.gnezdilov.web.form.transaction;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransactionAddForm {
    private String typeIds;
    private int sendingId;
    private int receivingId;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Positive
    private BigDecimal amount;
}
