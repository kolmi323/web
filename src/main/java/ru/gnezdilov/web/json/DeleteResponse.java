package ru.gnezdilov.web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

@Data
@AllArgsConstructor
public class DeleteResponse extends AbstractResponse {
    private boolean isDelete;
}
