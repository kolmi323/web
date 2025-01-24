package ru.gnezdilov.service.converter;

import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.AccountDTO;

public class ConverterAccountModelToAccountDTO implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        if (source == null) {
            return null;
        }
        return new AccountDTO(source.getId(), source.getUserId(), source.getName(), source.getBalance());
    }
}
