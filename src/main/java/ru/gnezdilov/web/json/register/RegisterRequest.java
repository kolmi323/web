package ru.gnezdilov.web.json.register;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class RegisterRequest implements AbstractRequest {
    private String name;
    private String email;
    private String password;
}
