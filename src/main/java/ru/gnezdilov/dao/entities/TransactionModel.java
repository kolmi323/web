package ru.gnezdilov.dao.entities;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({

})
public class TransactionModel implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "from_account_id")
    private int senderAccountId;
    @Column(name = "to_account_id")
    private int receiverAccountId;
    @Column(nullable = false, name = "amount")
    private BigDecimal amount;
    @Column(nullable = false, name = "date")
    private LocalDate date;

    @Override
    public String toString() {
        return id + ". " + date +": " + amount;
    }
}
