package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.EmptyRequest;
import ru.gnezdilov.web.json.type.show.TypeShowResponse;

import java.util.List;

@Service("/type/show")
@RequiredArgsConstructor
public class TypeShowController extends AbstractSecureController<EmptyRequest, TypeShowResponse> {
    private final TypeService typeService;

    @Override
    public TypeShowResponse handle(EmptyRequest request, int userId) {
        List<TypeDTO> types = typeService.getAll(userId);
        return new TypeShowResponse(types);
    }

    @Override
    public Class<EmptyRequest> getRequestClass() {
        return EmptyRequest.class;
    }
}
