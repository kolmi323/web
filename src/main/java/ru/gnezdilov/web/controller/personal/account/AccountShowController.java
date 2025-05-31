package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.SpringContext;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.EmptyRequest;
import ru.gnezdilov.web.json.ListResponse;

import java.util.List;

@Service("/account/show")
@RequiredArgsConstructor
public class AccountShowController extends AbstractSecureController<EmptyRequest, ListResponse<AccountDTO>> {
    private final AccountService accountService;

    @Override
    public ListResponse<AccountDTO> handle(EmptyRequest request, int userId) {
        List<AccountDTO> accounts = accountService.getAll(userId);
        return new ListResponse(accounts);
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }
}
