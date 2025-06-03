package ru.gnezdilov.web.json.transaction.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionAddRequest implements AbstractRequest {
    private Integer[] typesIds;
    private int sendingId;
    private int receivingId;
    private BigDecimal amount;
}
