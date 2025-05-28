package ru.gnezdilov.web.json.register;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class RegisterRequest extends AbstractRequest {
    private String name;
    private String email;
    private String password;
}
