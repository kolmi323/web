package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.service.converter.web.ConverterTransactionDTOToTransactionAddResponse;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.web.json.transaction.add.TransactionAddRequest;
import ru.gnezdilov.web.json.transaction.add.TransactionAddResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final ConverterTransactionDTOToTransactionAddResponse converter;

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<TransactionAddResponse> add(@RequestBody @Valid TransactionAddRequest request,
                                                                    HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
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
