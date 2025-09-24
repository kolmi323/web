package ru.gnezdilov.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterForm {
    @NotEmpty
    private String name;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String password;
}
