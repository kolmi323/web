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
