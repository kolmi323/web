package ru.gnezdilov.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.Model;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TypeModel extends Model {
    private int userId;
    private String name;

    public TypeModel(int id, int userId, String name) {
        this.setId(id);
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Название типа транзакции: " + this.name;
    }
}
