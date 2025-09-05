package ru.gnezdilov.api.json.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.api.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class RegisterResponse implements AbstractResponse {
    private int id;
    private String email;
}
