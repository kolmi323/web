package s03.dao.Model;

import s03.dao.AbstractClass.Model;

import java.math.BigDecimal;

public class AccountModel extends Model {
    private int userId;
    private String name;
    private BigDecimal balance;

    public AccountModel() {
    }

    public AccountModel(String name, BigDecimal balance) {
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
        return "Название счета: " + name +
                ", баланс на счету = " + balance;
    }
}
