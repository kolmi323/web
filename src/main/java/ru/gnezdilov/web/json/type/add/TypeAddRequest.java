package ru.gnezdilov.web.json.type.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class TypeAddRequest extends AbstractRequest {
    private String name;
}
