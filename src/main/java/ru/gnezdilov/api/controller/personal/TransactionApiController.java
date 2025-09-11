package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.ApiController;
import ru.gnezdilov.api.converter.ConverterTransactionDTOToTransactionAddResponse;
import ru.gnezdilov.api.json.transaction.add.TransactionAddRequest;
import ru.gnezdilov.api.json.transaction.add.TransactionAddResponse;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionApiController extends ApiController {
    private final TransactionService transactionService;
    private final ConverterTransactionDTOToTransactionAddResponse converter;

    @PostMapping("/add")
    public ResponseEntity<TransactionAddResponse> add(@RequestBody @Valid TransactionAddRequest request,
                                                                    HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        TransactionDTO transaction = transactionService.create(
                Arrays.asList(request.getTypesIds()), userId,
                request.getSendingId(), request.getReceivingId(), request.getAmount()
        );
        if (transaction == null) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ok(Objects.requireNonNull(converter.convert(transaction)));
    }
}
