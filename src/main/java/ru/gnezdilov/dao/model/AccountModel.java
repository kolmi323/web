package ru.gnezdilov.dao.model;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AccountModel implements HasId {
    private int userId;
    private int id;
    private String name;
    private BigDecimal balance;

    public AccountModel(int id, int userId, String name, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Название счета: " + name +
                ", баланс на счету = " + balance;
    }
}
