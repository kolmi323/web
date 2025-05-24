package ru.gnezdilov.web.json.transaction.add;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionAddResponse {
    private int id;
    private BigDecimal amount;
}
