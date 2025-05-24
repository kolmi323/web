package ru.gnezdilov.web.json.transaction.add;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionAddRequest {
    private Integer[] typesIds;
    private int sendingId;
    private int receivingId;
    private BigDecimal amount;
}
