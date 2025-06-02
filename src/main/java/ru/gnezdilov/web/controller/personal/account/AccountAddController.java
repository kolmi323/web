package ru.gnezdilov.web.controller.personal.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.converter.ConverterAccountDTOToAccountAddResponse;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.account.create.AccountAddRequest;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

@Service("/account/add")
@RequiredArgsConstructor
public class AccountAddController extends AbstractSecureController<AccountAddRequest, AccountAddResponse> {
    private final AccountService accountService;
    private final ConverterAccountDTOToAccountAddResponse converter;

    @Override
    public AccountAddResponse handle(AccountAddRequest request, int userId) {
        AccountDTO accountDTO = accountService.create(userId, request.getName(), request.getBalance());
        return converter.convert(accountDTO);
    }

    @Override
    public Class<AccountAddRequest> getRequestClass() {
        return AccountAddRequest.class;
    }
}
