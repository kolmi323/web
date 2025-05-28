package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.service.custominterface.HasId;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AccountDTO implements HasId {
    private int id;
    private int userId;
    private String name;
    private BigDecimal balance;

    public AccountDTO(int id, int userId, String name, BigDecimal balance) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return this.id + ". Название счета: " + this.name +
                ", баланс на счету = " + this.balance;
    }
}
