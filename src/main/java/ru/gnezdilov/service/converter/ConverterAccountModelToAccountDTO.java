package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.entities.AccountModel;
import org.springframework.core.convert.converter.Converter;
import ru.gnezdilov.service.dto.AccountDTO;

@Service
public class ConverterAccountModelToAccountDTO implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        return new AccountDTO(source.getId(), source.getUserId(), source.getName(), source.getBalance());
    }
}
