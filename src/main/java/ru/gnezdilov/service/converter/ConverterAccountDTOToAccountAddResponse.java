package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

@Service
public class ConverterAccountDTOToAccountAddResponse implements Converter<AccountDTO, AccountAddResponse> {
    @Override
    public AccountAddResponse convert(AccountDTO source) {
        if (source == null) {
            return null;
        }
        return new AccountAddResponse(source.getId(), source.getName(), source.getBalance());
    }
}
