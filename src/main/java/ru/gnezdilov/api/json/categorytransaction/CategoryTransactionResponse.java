package ru.gnezdilov.api.json.categorytransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.api.interfaces.AbstractResponse;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryTransactionResponse implements AbstractResponse {
    private Map<String, BigDecimal> transactions;
}
