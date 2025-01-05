package ru.gnezdilov.service.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private int id;
    private int userId;
    private String name;
    private BigDecimal balance;

    public AccountDTO() {
    }

    public AccountDTO(int id, int userId, String name, BigDecimal balance) {
        this.id = id;
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
}
