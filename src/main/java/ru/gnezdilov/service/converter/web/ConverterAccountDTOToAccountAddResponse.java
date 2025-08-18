package ru.gnezdilov.service.converter.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

@Service
public class ConverterAccountDTOToAccountAddResponse implements Converter<AccountDTO, AccountAddResponse> {
    @Override
    public AccountAddResponse convert(AccountDTO source) {
        return new AccountAddResponse(source.getId(), source.getName(), source.getBalance());
    }
}
