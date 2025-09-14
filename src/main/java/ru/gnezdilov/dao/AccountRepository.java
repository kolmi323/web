package ru.gnezdilov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gnezdilov.dao.entities.AccountModel;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Integer> {
    boolean existsByIdAndUserId(Integer id, Integer userId);

    AccountModel findByIdAndUserId(Integer id, Integer userId);

    List<AccountModel> findAllByUserId(Integer userId);

    @Transactional
    Integer deleteByIdAndUserId(Integer id, Integer userId);

    AccountModel findByIdAndUserIdAndBalanceIsGreaterThanEqual(Integer id, Integer userId, BigDecimal balance);
}
