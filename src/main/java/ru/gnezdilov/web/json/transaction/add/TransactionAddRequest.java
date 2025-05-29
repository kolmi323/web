package ru.gnezdilov.web.json.transaction.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
@Setter
public class TransactionAddRequest extends AbstractRequest {
    private Integer[] typesIds;
    private int sendingId;
    private int receivingId;
    private BigDecimal amount;
}
