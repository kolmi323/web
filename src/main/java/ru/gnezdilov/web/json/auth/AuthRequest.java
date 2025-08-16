package ru.gnezdilov.web.json.auth;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthRequest implements AbstractRequest {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
