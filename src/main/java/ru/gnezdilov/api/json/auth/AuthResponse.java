package ru.gnezdilov.api.json.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.api.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class AuthResponse implements AbstractResponse {
    private Integer id;
    private String email;
}
