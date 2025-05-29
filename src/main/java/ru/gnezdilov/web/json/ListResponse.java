package ru.gnezdilov.web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.service.custominterface.HasId;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class ListResponse<DTO extends HasId> extends AbstractResponse {
    private List<DTO> list;
}
