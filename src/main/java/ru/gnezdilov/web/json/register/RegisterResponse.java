package ru.gnezdilov.web.json.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class RegisterResponse implements AbstractResponse {
    private int id;
    private String email;
}
