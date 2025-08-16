package ru.gnezdilov.web.controller.personal.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.transaction.add.TransactionAddRequest;
import ru.gnezdilov.web.json.transaction.add.TransactionAddResponse;

import java.util.Arrays;


@RequiredArgsConstructor
public class TransactionAddController extends AbstractSecureController<TransactionAddRequest, TransactionAddResponse> {
    private final TransactionService transactionService;

    @Override
    public TransactionAddResponse handle(TransactionAddRequest request, int userId) {
        TransactionDTO transactionDTO = transactionService.create(
                Arrays.asList(request.getTypesIds()),userId,
                request.getSendingId(), request.getReceivingId(), request.getAmount()
        );
        return new TransactionAddResponse(transactionDTO.getId(), transactionDTO.getAmount());
    }

    @Override
    public Class<TransactionAddRequest> getRequestClass() {
        return TransactionAddRequest.class;
    }
}
