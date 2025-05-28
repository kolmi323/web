package ru.gnezdilov.web.json.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse extends AbstractResponse {
    private Integer id;
    private String email;
}
