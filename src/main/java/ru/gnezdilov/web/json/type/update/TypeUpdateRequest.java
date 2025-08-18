package ru.gnezdilov.web.json.type.update;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.web.interfaces.AbstractRequest;

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
