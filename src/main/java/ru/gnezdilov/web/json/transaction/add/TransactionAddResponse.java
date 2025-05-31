package ru.gnezdilov.web.json.transaction.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionAddResponse implements AbstractResponse {
    private int id;
    private BigDecimal amount;
}
