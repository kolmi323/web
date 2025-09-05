package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.ApiController;
import ru.gnezdilov.api.json.categorytransaction.CategoryTransactionRequest;
import ru.gnezdilov.api.json.categorytransaction.CategoryTransactionResponse;
import ru.gnezdilov.service.personal.CategoryTransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportTransactionApiController extends ApiController {
    private final CategoryTransactionService categoryTransactionService;

    @GetMapping("/incoming")
    public @ResponseBody ResponseEntity<CategoryTransactionResponse> getIncomingTransaction(@RequestBody @Valid CategoryTransactionRequest request,
                                                                                            HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, BigDecimal> transactions = categoryTransactionService.getIncomingTransactions(userId,
                request.getDateAfter(), request.getDateBefore());
        return ok(new CategoryTransactionResponse(transactions));
    }

    @GetMapping("/outgoing")
    public @ResponseBody ResponseEntity<CategoryTransactionResponse> getOutgoingTransaction(@RequestBody @Valid CategoryTransactionRequest request,
                                                                                            HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, BigDecimal> transactions =  categoryTransactionService.getOutgoingTransactions(userId,
                request.getDateAfter(), request.getDateBefore());
        return ok(new CategoryTransactionResponse(transactions));
    }
}
