package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.AccountRepository;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final ConverterAccountModelToAccountDTO converter;

    public boolean existsById(int id, int userId) {
        return this.accountRepository.existsByIdAndUserId(id, userId);
    }

    public AccountDTO getById(int id, int userId) {
        return converter.convert(this.accountRepository.findByIdAndUserId(id, userId));
    }

    public List<AccountDTO> getAll(int userId) {
        return this.accountRepository.findAllByUserId(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public AccountDTO create(int userId, String name, BigDecimal balance) {
        AccountModel account = new AccountModel();
        account.setUserId(userId);
        account.setName(name);
        account.setBalance(balance);
        AccountModel accountModel = this.accountRepository.save(account);
        return converter.convert(accountModel);
    }

    public boolean delete(int id, int userId) {
        return this.accountRepository.deleteByIdAndUserId(id, userId) == 1;
    }
}