package ru.gnezdilov.web.json.type.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

@Getter
@Setter
public class TypeAddRequest implements AbstractRequest {
    private String name;
}
