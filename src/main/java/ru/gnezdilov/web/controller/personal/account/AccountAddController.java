package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.account.create.AccountAddRequest;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

@Service("/account/add")
@RequiredArgsConstructor
public class AccountAddController extends AbstractSecureController<AccountAddRequest, AccountAddResponse> {
    private final AccountService accountService;

    @Override
    public AccountAddResponse handle(AccountAddRequest request, int userId) {
        AccountDTO accountDTO = accountService.create(userId, request.getName(), request.getBalance());
        return new AccountAddResponse(accountDTO.getId(), accountDTO.getName(), accountDTO.getBalance());
    }

    @Override
    public Class<AccountAddRequest> getRequestClass() {
        return AccountAddRequest.class;
    }
}
