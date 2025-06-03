package ru.gnezdilov.web.json.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class AuthResponse implements AbstractResponse {
    private Integer id;
    private String email;
}
