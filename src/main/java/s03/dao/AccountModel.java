package s03.dao;

import java.math.BigDecimal;

public class AccountModel {
    private int id;
    private String name;
    private BigDecimal balance;

    public AccountModel() {
    }

    public AccountModel(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "Название счета:" + name +
                ", Баланс на счету=" + balance +
                "}\n";
    }
}
