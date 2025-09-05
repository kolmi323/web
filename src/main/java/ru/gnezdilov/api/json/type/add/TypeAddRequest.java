package ru.gnezdilov.api.json.type.add;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TypeAddRequest implements AbstractRequest {
    @NotEmpty
    private String name;
}
