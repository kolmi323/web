package ru.gnezdilov.web.json.type.update;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class TypeUpdateRequest implements AbstractRequest {
    private int id;
    private String newName;
}
