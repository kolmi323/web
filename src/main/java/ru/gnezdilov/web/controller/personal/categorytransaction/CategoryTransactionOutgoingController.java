package ru.gnezdilov.web.controller.personal.categorytransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.categorytransaction.CategoryTransactionRequest;
import ru.gnezdilov.web.json.categorytransaction.CategoryTransactionResponse;

import java.math.BigDecimal;
import java.util.Map;

@Service("/report/outgoing")
@RequiredArgsConstructor
public class CategoryTransactionOutgoingController implements SecureController<CategoryTransactionRequest, CategoryTransactionResponse> {
    private final CategoryTransactionService categoryTransactionService;

    @Override
    public CategoryTransactionResponse handle(CategoryTransactionRequest request, int userId) {
        Map<String, BigDecimal> transactions = categoryTransactionService.getOutgoingTransactions(userId, request.getDateAfter(), request.getDateBefore());
        if (!transactions.isEmpty()) {
            return new CategoryTransactionResponse(transactions);
        }
        return null;
    }

    @Override
    public Class<CategoryTransactionRequest> getRequestClass() {
        return CategoryTransactionRequest.class;
    }
}
