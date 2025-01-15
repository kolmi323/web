package ru.gnezdilov.service.personal;

import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.dao.AccountDAO;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.service.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AccountService {
    private AccountDAO accountDAO;
    private final ConverterAccountModelToAccountDTO converter;

    public AccountService(AccountDAO dao, ConverterAccountModelToAccountDTO converter) {
        this.accountDAO = dao;
        this.converter = converter;
    }

    public List<AccountDTO> getAll(int userId) {
        return this.accountDAO.getAll(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public AccountDTO create(int userId, String name, BigDecimal balance) {
        AccountModel accountModel = this.accountDAO.insert(userId, name, balance);
        return converter.convert(accountModel);
    }

    public boolean delete(int id, int userId) {
        return this.accountDAO.delete(id, userId);
    }
}
