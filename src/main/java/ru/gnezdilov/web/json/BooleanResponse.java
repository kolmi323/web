package ru.gnezdilov.web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class BooleanResponse implements AbstractResponse {
    private boolean isTrue;
}
