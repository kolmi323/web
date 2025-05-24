package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.interfaces.InformationController;
import ru.gnezdilov.web.json.type.show.TypeShowResponse;

import java.util.List;

@Service("/type/show")
@RequiredArgsConstructor
public class TypeShowController implements InformationController<TypeShowResponse> {
    private final TypeService typeService;

    @Override
    public TypeShowResponse handle(int userId) {
        List<TypeDTO> types = typeService.getAll(userId);
        if (types != null) {
            return new TypeShowResponse(types);
        }
        return null;
    }
}
