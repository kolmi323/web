package ru.gnezdilov.service.dto;

import lombok.*;
import ru.gnezdilov.dao.abstractclass.DTO;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserDTO extends DTO {
    private String name;
    private String email;

    public UserDTO(int id, String name, String email) {
        this.setId(id);
        this.name = name;
        this.email = email;
    }
}
