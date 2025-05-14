package ru.gnezdilov.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionModel extends Model {
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private LocalDate date;

    public TransactionModel(int id, int senderAccountId, int receiverAccountId, BigDecimal amount, LocalDate date) {
        this.setId(id);
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return id + ". " + date +": " + amount;
    }
}
