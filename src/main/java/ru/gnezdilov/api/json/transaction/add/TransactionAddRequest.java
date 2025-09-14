package ru.gnezdilov.api.json.transaction.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TransactionAddRequest implements AbstractRequest {
    private ArrayList<Integer> typesIds;
    private int sendingId;
    private int receivingId;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Positive
    private BigDecimal amount;
}
