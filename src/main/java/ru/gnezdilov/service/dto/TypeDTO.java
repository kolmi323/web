package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.DTO;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TypeDTO extends DTO {
    private int userId;
    private String name;

    public TypeDTO(int id, int userId, String name) {
        this.setId(id);
        this.userId = userId;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name;
    }
}
