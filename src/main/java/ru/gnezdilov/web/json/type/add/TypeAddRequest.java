package ru.gnezdilov.web.json.type.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TypeAddRequest implements AbstractRequest {
    @NotEmpty
    private String name;
}
