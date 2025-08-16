package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.type.update.TypeUpdateRequest;
import ru.gnezdilov.web.json.type.update.TypeUpdateResponse;

@RequiredArgsConstructor
public class TypeUpdateController extends AbstractSecureController<TypeUpdateRequest, TypeUpdateResponse> {
    private final TypeService typeService;

    @Override
    public TypeUpdateResponse handle(TypeUpdateRequest request, int userId) {
        TypeDTO typeDTO = typeService.edit(request.getId(), userId, request.getNewName());
        return new TypeUpdateResponse(typeDTO.getId(), typeDTO.getName());
    }

    @Override
    public Class<TypeUpdateRequest> getRequestClass() {
        return TypeUpdateRequest.class;
    }
}
