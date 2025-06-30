package ru.gnezdilov.dao.entities;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

import javax.persistence.*;

@Entity
@Table(name = "type")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Type.findByUserId",
                query = "SELECT t FROM TypeModel AS t WHERE t.userId = :userId"),
        @NamedQuery(name = "Type.findByIdAndUserId",
                query = "SELECT t FROM TypeModel AS t WHERE t.id = :id AND t.userId = :userId"),
        @NamedQuery(name = "Type.findByUserIdAndName",
                query = "SELECT t FROM TypeModel AS t WHERE t.userId = :userId AND t.name = :name")
})
public class TypeModel implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, name = "user_id")
    private int userId;
    @Column(nullable = false, name = "name")
    private String name;

    @Override
    public String toString() {
        return "Название типа транзакции: " + this.name;
    }
}
