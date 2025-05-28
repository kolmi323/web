package ru.gnezdilov.service.dto;

import lombok.*;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserDTO implements HasId {
    private int id;
    private String name;
    private String email;

    public UserDTO(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
