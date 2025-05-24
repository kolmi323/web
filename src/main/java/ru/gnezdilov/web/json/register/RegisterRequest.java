package ru.gnezdilov.web.json.register;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

@Getter
public class RegisterRequest extends AbstractRequest {
    private String name;
    private String email;
    private String password;

    public void setName(String name) {
        this.name = this.extractString("name", name);
    }

    public void setEmail(String email) {
        this.email = this.extractEmail(email);
    }

    public void setPassword(String password) {
        this.password = this.extractPassword(password);
    }
}
