package ru.gnezdilov.web.json.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

@Data
@AllArgsConstructor
public class RegisterResponse extends AbstractResponse {
    private int id;
    private String email;
}
