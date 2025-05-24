package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.account.show.AccountShowRequest;
import ru.gnezdilov.web.json.account.show.AccountShowResponse;

import java.util.List;

@Service("/account/show")
@RequiredArgsConstructor
public class AccountShowController implements SecureController<AccountShowRequest, AccountShowResponse> {
    private final AccountService accountService;

    @Override
    public AccountShowResponse handle(AccountShowRequest request, int userId) {
        List<AccountDTO> accounts = accountService.getAll(userId);
        if (!accounts.isEmpty()) {
            return new AccountShowResponse(accounts);
        }
        return null;
    }

    @Override
    public Class<AccountShowRequest> getRequestClass() {
        return AccountShowRequest.class;
    }
}
