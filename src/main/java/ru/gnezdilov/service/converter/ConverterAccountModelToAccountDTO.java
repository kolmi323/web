package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.AccountDTO;

@Service
public class ConverterAccountModelToAccountDTO implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        if (source == null) {
            return null;
        }
        return new AccountDTO(source.getId(), source.getUserId(), source.getName(), source.getBalance());
    }
}
