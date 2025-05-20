package ru.gnezdilov.web.json.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private Integer id;
    private String email;
}
