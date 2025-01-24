package ru.gnezdilov.service.dto;

import ru.gnezdilov.dao.abstractclass.DTO;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDTO extends DTO {
    private int userId;
    private String name;
    private BigDecimal balance;

    public AccountDTO() {
    }

    public AccountDTO(int id, int userId, String name, BigDecimal balance) {
        this.setId(id);
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return this.id + ". Название счета: " + this.name +
                ", баланс на счету = " + this.balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
