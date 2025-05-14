package ru.gnezdilov.dao.model;

import lombok.*;
import ru.gnezdilov.dao.abstractclass.Model;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AccountModel extends Model {
    private int userId;
    private String name;
    private BigDecimal balance;

    public AccountModel(int id, int userId, String name, BigDecimal balance) {
        this.setId(id);
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
