package ru.gnezdilov.api.json.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.api.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class TypeResponse implements AbstractResponse {
    private int id;
    private String name;
}
