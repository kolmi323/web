package ru.gnezdilov.web.json.categorytransaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class CategoryTransactionResponse {
    private Map<String, BigDecimal> transactions;
}
