package ru.gnezdilov.web.json.type.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class TypeShowResponse extends AbstractResponse {
    private List<TypeDTO> types;
}
