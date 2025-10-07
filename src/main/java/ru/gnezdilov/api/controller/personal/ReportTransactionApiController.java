package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gnezdilov.api.controller.ApiController;
import ru.gnezdilov.api.json.categorytransaction.CategoryTransactionRequest;
import ru.gnezdilov.service.personal.CategoryTransactionService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportTransactionApiController extends ApiController {
    private final CategoryTransactionService categoryTransactionService;

    @GetMapping("/incoming")
    public Map<String, BigDecimal> getIncomingTransaction(@RequestBody @Valid CategoryTransactionRequest request) {
        Integer userId = this.currentUser().getId();
        Map<String, BigDecimal> transactions = categoryTransactionService.getIncomingTransactions(userId,
                request.getDateAfter(), request.getDateBefore());
        return transactions;
    }

    @GetMapping("/outgoing")
    public Map<String, BigDecimal> getOutgoingTransaction(@RequestBody @Valid CategoryTransactionRequest request) {
        Integer userId = this.currentUser().getId();
        Map<String, BigDecimal> transactions =  categoryTransactionService.getOutgoingTransactions(userId,
                request.getDateAfter(), request.getDateBefore());
        return transactions;
    }
}
