package ru.gnezdilov.web.json.type.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class TypeUpdateResponse implements AbstractResponse {
    private int id;
    private String newName;
}
