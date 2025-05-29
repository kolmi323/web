package ru.gnezdilov.web.json.type.delete;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class TypeDeleteRequest extends AbstractRequest {
    private int id;
}
