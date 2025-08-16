package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.DeleteResponse;
import ru.gnezdilov.web.json.account.delete.AccountDeleteRequest;


@RequiredArgsConstructor
public class AccountDeleteController extends AbstractSecureController<AccountDeleteRequest, DeleteResponse> {
    private final AccountService accountService;

    @Override
    public DeleteResponse handle(AccountDeleteRequest request, int userId) {
        boolean isDelete = accountService.delete(request.getId(), userId);
        return new DeleteResponse(isDelete);
    }

    @Override
    public Class<AccountDeleteRequest> getRequestClass() {
        return AccountDeleteRequest.class;
    }
}
