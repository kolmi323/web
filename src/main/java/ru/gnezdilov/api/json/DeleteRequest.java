package ru.gnezdilov.api.json;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DeleteRequest implements AbstractRequest {
    @NotNull
    private Integer id;
}
