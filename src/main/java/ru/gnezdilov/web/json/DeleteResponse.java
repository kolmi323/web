package ru.gnezdilov.web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.interfaces.AbstractResponse;

@Data
@AllArgsConstructor
public class DeleteResponse implements AbstractResponse {
    private boolean isDelete;
}
