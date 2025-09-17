package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.AccountRepository;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.NotFoundException;
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

    public boolean existsByIdAndUserId(int id, int userId) {
        return this.accountRepository.existsByIdAndUserId(id, userId);
    }

    public AccountDTO getByIdAndUserId(int id, int userId) {
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

    @Transactional(rollbackFor = {NotFoundException.class, IllegalArgumentException.class})
    public void delete(int id, int userId) {
        int countModifiedRecords = this.accountRepository.deleteByIdAndUserId(id, userId);
        if (countModifiedRecords == 0) {
            throw new NotFoundException("Account with id " + id + " not found");
        } else if (countModifiedRecords > 1) {
            throw new IllegalArgumentException("Deletion of account with id " + id + " failed");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateFromAccount(int userId, int fromAccountId, BigDecimal amount) {
        AccountModel accountModel = accountRepository.findByIdAndUserIdAndBalanceIsGreaterThanEqual(fromAccountId, userId, amount);
        accountModel.setBalance(accountModel.getBalance().subtract(amount));
        accountRepository.save(accountModel);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateToAccount(int userId, int toAccountId, BigDecimal amount) {
        AccountModel accountModel = accountRepository.findByIdAndUserId(toAccountId, userId);
        accountModel.setBalance(accountModel.getBalance().add(amount));
        accountRepository.save(accountModel);
    }
}