package ru.gnezdilov.dao.entities;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import javax.persistence.*;

@Entity
@Table(name = "type_transaction")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTransactionModel implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, name = "type_id")
    private int typeId;
    @Column(nullable = false, name = "transaction_id")
    private int transactionId;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
