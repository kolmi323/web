package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.controller.ApiController;
import ru.gnezdilov.api.converter.ConverterTransactionDTOToTransactionAddResponse;
import ru.gnezdilov.api.json.transaction.add.TransactionAddRequest;
import ru.gnezdilov.api.json.transaction.add.TransactionAddResponse;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionApiController extends ApiController {
    private final TransactionService transactionService;
    private final ConverterTransactionDTOToTransactionAddResponse converter;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionAddResponse add(@RequestBody @Valid TransactionAddRequest request,
                                                                    HttpServletRequest httpServletRequest) {
        Integer userId = this.extractUserId(httpServletRequest);
        TransactionDTO transaction = transactionService.create(
                Arrays.asList(request.getTypesIds()), userId,
                request.getSendingId(), request.getReceivingId(), request.getAmount()
        );
        return converter.convert(transaction);
    }
}
