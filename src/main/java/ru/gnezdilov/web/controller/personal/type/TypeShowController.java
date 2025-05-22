package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.type.show.TypeShowRequest;
import ru.gnezdilov.web.json.type.show.TypeShowResponse;

import java.util.List;

@Service("/type/show")
@RequiredArgsConstructor
public class TypeShowController implements SecureController<TypeShowRequest, TypeShowResponse> {
    private final TypeService typeService;

    @Override
    public TypeShowResponse handle(TypeShowRequest request, int userId) {
        List<TypeDTO> types = typeService.getAll(userId);
        if (types != null) {
            return new TypeShowResponse(types);
        }
        return null;
    }

    @Override
    public Class<TypeShowRequest> getRequestClass() {
        return TypeShowRequest.class;
    }
}
