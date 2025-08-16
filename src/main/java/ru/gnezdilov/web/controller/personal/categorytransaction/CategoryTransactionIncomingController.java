package ru.gnezdilov.web.controller.personal.categorytransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.categorytransaction.CategoryTransactionRequest;
import ru.gnezdilov.web.json.categorytransaction.CategoryTransactionResponse;

import java.math.BigDecimal;
import java.util.Map;


@RequiredArgsConstructor
public class CategoryTransactionIncomingController extends AbstractSecureController<CategoryTransactionRequest, CategoryTransactionResponse> {
    private final CategoryTransactionService categoryTransactionService;

    @Override
    public CategoryTransactionResponse handle(CategoryTransactionRequest request, int userId) {
        Map<String, BigDecimal> transactions = categoryTransactionService.getIncomingTransactions(userId, request.getDateAfter(), request.getDateBefore());
        return new CategoryTransactionResponse(transactions);
    }

    @Override
    public Class<CategoryTransactionRequest> getRequestClass() {
        return CategoryTransactionRequest.class;
    }
}
