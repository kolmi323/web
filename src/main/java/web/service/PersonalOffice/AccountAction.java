package web.service.PersonalOffice;

import web.converter.ConverterAccountModelToAccountDTO;
import web.dao.DaoFactory;
import web.dao.Model.AccountModel;
import web.service.DTO.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AccountAction {
    private final ConverterAccountModelToAccountDTO convertAccountModelToAccountDTO;

    public AccountAction() {
        this.convertAccountModelToAccountDTO = new ConverterAccountModelToAccountDTO();
    }

    public List<AccountDTO> getAll(int userId) {
        return DaoFactory.getAccountDao().getListAccount(userId).stream()
                .map(this.convertAccountModelToAccountDTO::convert).collect(Collectors.toList());
    }

    public AccountDTO create(int userId, String name, BigDecimal balance) {
        AccountModel accountModel = DaoFactory.getAccountDao().insertAccount(userId, name, balance);
        return convertAccountModelToAccountDTO.convert(accountModel);
    }

    public boolean delete(int userId, String name) {
        return DaoFactory.getAccountDao().deleteAccount(userId, name);
    }
}
