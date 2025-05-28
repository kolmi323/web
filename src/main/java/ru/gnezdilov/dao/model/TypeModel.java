package ru.gnezdilov.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TypeModel implements HasId {
    private int userId;
    private int id;
    private String name;

    public TypeModel(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Название типа транзакции: " + this.name;
    }
}
