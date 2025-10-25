package ru.gnezdilov.service.dto;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO implements HasId {
    private int id;
    private int userId;
    private String name;

    @Override
    public String toString() {
        return this.id + ". " + this.name;
    }
}
