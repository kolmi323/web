package ru.gnezdilov.web.json.register;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterRequest implements AbstractRequest {
    @NotEmpty
    private String name;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String password;
}
