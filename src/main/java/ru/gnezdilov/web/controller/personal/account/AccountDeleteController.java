package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.account.delete.AccountDeleteRequest;
import ru.gnezdilov.web.json.account.delete.AccountDeleteResponse;

@Service("/account/delete")
@RequiredArgsConstructor
public class AccountDeleteController implements SecureController<AccountDeleteRequest, AccountDeleteResponse> {
    private final AccountService accountService;

    @Override
    public AccountDeleteResponse handle(AccountDeleteRequest request, int userId) {
        boolean isDelete = accountService.delete(request.getAccountId(), userId);
        return new AccountDeleteResponse(isDelete);
    }

    @Override
    public Class<AccountDeleteRequest> getRequestClass() {
        return AccountDeleteRequest.class;
    }
}
