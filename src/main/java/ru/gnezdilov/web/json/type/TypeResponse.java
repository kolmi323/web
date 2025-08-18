package ru.gnezdilov.web.json.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class TypeResponse implements AbstractResponse {
    private int id;
    private String name;
}
