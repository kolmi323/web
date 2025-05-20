package ru.gnezdilov.web.json.register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private int id;
    private String email;
}
