package ru.gnezdilov.web.controller.personal.categorytransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.categorytransaction.incoming.CategoryTransactionRequest;
import ru.gnezdilov.web.json.categorytransaction.incoming.CategoryTransactionResponse;

import java.math.BigDecimal;
import java.util.Map;

@Service("/report/incoming")
@RequiredArgsConstructor
public class CategoryTransactionIncomingController implements SecureController<CategoryTransactionRequest, CategoryTransactionResponse> {
    private final CategoryTransactionService categoryTransactionService;

    @Override
    public CategoryTransactionResponse handle(CategoryTransactionRequest request, int userId) {
        Map<String, BigDecimal> transactions = categoryTransactionService.getIncomingTransactions(userId, request.getDateAfter(), request.getDateBefore());
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
