package ru.gnezdilov.web.json.type.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

@Data
@AllArgsConstructor
public class TypeUpdateResponse extends AbstractResponse {
    private int id;
    private String newName;
}
