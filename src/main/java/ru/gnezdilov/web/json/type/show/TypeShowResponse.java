package ru.gnezdilov.web.json.type.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.gnezdilov.service.dto.TypeDTO;

import java.util.List;

@Data
@AllArgsConstructor
public class TypeShowResponse {
    private List<TypeDTO> types;
}
