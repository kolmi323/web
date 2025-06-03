package ru.gnezdilov.web.json.type.delete;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class TypeDeleteRequest implements AbstractRequest {
    private int id;
}
