package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionDTO extends DTO {
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private LocalDate date;

    public TransactionDTO(int id, int senderAccountId, int receiverAccountId, BigDecimal amount, LocalDate date) {
        this.setId(id);
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return id + ". " + date;
    }
}
