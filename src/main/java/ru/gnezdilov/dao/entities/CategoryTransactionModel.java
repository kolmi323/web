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
@NamedQueries({
        @NamedQuery(name = "Report.getOutgoingTransaction",
                query = "SELECT COALESCE(ty.name, 'no type'), SUM(tr.amount)\n" +
                        "FROM UserModel AS us\n" +
                        "   JOIN AccountModel AS ac ON us.id = ac.userId\n" +
                        "   JOIN TransactionModel AS tr ON ac.id = tr.senderAccountId\n" +
                        "   LEFT JOIN CategoryTransactionModel AS tt ON tr.id = tt.transactionId\n" +
                        "   LEFT JOIN TypeModel AS ty ON tt.typeId = ty.id\n" +
                        "WHERE us.id = :id AND tr.date > :startDate AND tr.date < :endDate\n" +
                        "GROUP BY ty.name"),
        @NamedQuery(name = "Report.getIncomingTransaction",
                query = "SELECT COALESCE(ty.name, 'no type'), SUM(tr.amount)\n" +
                        "FROM UserModel AS us\n" +
                        "   JOIN AccountModel AS ac ON us.id = ac.userId\n" +
                        "   JOIN TransactionModel AS tr ON ac.id = tr.receiverAccountId\n" +
                        "   LEFT JOIN CategoryTransactionModel AS tt ON tr.id = tt.transactionId\n" +
                        "   LEFT JOIN TypeModel AS ty ON tt.typeId = ty.id\n" +
                        "WHERE us.id = :id AND tr.date > :startDate AND tr.date < :endDate\n" +
                        "GROUP BY ty.name")
})
public class CategoryTransactionModel implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type_id")
    private int typeId;
    @Column(name = "transaction_id")
    private int transactionId;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
