package ru.gnezdilov.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
