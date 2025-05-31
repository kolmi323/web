package ru.gnezdilov.web.json.categorytransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryTransactionResponse implements AbstractResponse {
    private Map<String, BigDecimal> transactions;
}
