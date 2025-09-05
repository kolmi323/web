package ru.gnezdilov.api.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.api.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class BooleanResponse implements AbstractResponse {
    private boolean isTrue;
}
