package ru.gnezdilov.web.json.auth;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class AuthRequest implements AbstractRequest {
    private String email;
    private String password;
}
