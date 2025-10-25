package ru.gnezdilov.service.dto;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements HasId {
    private int id;
    private int userId;
    private String name;
    private BigDecimal balance;

    @Override
    public String toString() {
        return this.id + ". Название счета: " + this.name +
                ", баланс на счету = " + this.balance;
    }
}
