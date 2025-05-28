package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TypeDTO implements HasId {
    private int id;
    private int userId;
    private String name;

    public TypeDTO(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name;
    }
}
