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
public class UserModel extends Model {
    private String name;
    private String email;
    private String password;

    public UserModel(int id, String name, String email, String password) {
        this.setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
