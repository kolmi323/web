package ru.gnezdilov.service.dto;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements HasId {
    private int id;
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private LocalDate date;

    @Override
    public String toString() {
        return id + ". " + date;
    }
}
