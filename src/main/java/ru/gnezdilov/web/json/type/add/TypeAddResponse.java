package ru.gnezdilov.web.json.type.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class TypeAddResponse implements AbstractResponse {
    private int id;
    private String name;
}
