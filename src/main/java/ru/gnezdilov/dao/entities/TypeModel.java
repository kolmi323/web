package ru.gnezdilov.dao.entities;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "type")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class TypeModel implements HasId {
    public TypeModel(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(nullable = false, name = "user_id")
    @EqualsAndHashCode.Include
    private int userId;
    @Column(nullable = false, name = "name")
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany(mappedBy = "types")
    private Set<TransactionModel> transactions = new HashSet<>();

    @Override
    public String toString() {
        return "Название типа транзакции: " + this.name;
    }
}
