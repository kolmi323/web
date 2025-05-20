package ru.gnezdilov.web.json.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
