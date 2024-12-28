package web.converter;

import web.dao.Model.AccountModel;
import web.service.DTO.AccountDTO;

public class ConverterAccountModelToAccountDTO implements Converter<AccountModel, AccountDTO> {
    @Override
    public AccountDTO convert(AccountModel source) {
        return new AccountDTO(source.getUserId(), source.getName(), source.getBalance());
    }
}
