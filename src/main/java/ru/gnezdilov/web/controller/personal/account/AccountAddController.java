package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.account.create.AccountAddRequest;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

@Service("/account/add")
@RequiredArgsConstructor
public class AccountAddController implements SecureController<AccountAddRequest, AccountAddResponse> {
    private final AccountService accountService;

    @Override
    public AccountAddResponse handle(AccountAddRequest request, int userId) {
        AccountDTO accountDTO = accountService.create(userId, request.getName(), request.getBalance());
        if (accountDTO != null) {
            return new AccountAddResponse(accountDTO.getId(), accountDTO.getName(), accountDTO.getBalance());
        }
        return null;
    }

    @Override
    public Class<AccountAddRequest> getRequestClass() {
        return AccountAddRequest.class;
    }
}
