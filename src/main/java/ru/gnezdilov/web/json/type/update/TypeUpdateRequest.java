package ru.gnezdilov.web.json.type.update;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.abstractcustom.AbstractRequest;

@Getter
@Setter
public class TypeUpdateRequest extends AbstractRequest {
    private int id;
    private String newName;
}
