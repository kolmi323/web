package ru.gnezdilov.web.json.auth;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class AuthRequest extends AbstractRequest {
    private String email;
    private String password;
}
