package ru.gnezdilov.web.json.account.delete;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class AccountDeleteRequest implements AbstractRequest {
    private int id;
}
