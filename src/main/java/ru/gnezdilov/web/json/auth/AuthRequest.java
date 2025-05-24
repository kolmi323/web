package ru.gnezdilov.web.json.auth;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

@Getter
public class AuthRequest extends AbstractRequest {
    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = this.extractEmail(email);
    }

    public void setPassword(String password) {
        this.password = this.extractPassword(password);
    }
}
