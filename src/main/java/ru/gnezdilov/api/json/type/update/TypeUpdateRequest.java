package ru.gnezdilov.api.json.type.update;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TypeUpdateRequest implements AbstractRequest {
    @NotNull
    private Integer id;
    @NotEmpty
    private String newName;
}
