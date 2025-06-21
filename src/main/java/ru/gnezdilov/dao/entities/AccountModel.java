package ru.gnezdilov.dao.entities;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Account.findByIdAndUserId",
                query = "SELECT a FROM AccountModel AS a WHERE a.id = :id AND a.userId = :userId"),
        @NamedQuery(name = "Account.findByUserId",
                query = "SELECT a FROM AccountModel AS a WHERE a.userId = :userId"),
        @NamedQuery(name = "Account.findByIdAndUserIdWhereBalanceGreater",
                query = "SELECT a FROM AccountModel AS a WHERE a.id = :id AND a.userId = :userId AND a.balance >= :balance")
})
public class AccountModel implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, name = "user_id")
    private int userId;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "balance")
    private BigDecimal balance;

    @Override
    public String toString() {
        return "Название счета: " + name +
                ", баланс на счету = " + balance;
    }
}
