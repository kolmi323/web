package ru.gnezdilov.api.json.auth;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthRequest implements AbstractRequest {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
