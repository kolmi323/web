package ru.gnezdilov.web.json.categorytransaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryTransactionResponse extends AbstractResponse {
    private Map<String, BigDecimal> transactions;
}
