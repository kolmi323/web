package ru.gnezdilov.web.json.account.delete;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class AccountDeleteRequest extends AbstractRequest {
    private int id;
}
