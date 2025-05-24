package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.type.delete.TypeDeleteRequest;
import ru.gnezdilov.web.json.type.delete.TypeDeleteResponse;

@Service("/type/delete")
@RequiredArgsConstructor
public class TypeDeleteController implements SecureController<TypeDeleteRequest, TypeDeleteResponse> {
    private final TypeService typeService;

    @Override
    public TypeDeleteResponse handle(TypeDeleteRequest request, int userId) {
        boolean isDelete = typeService.delete(request.getId(), userId);
        return new TypeDeleteResponse(isDelete);
    }

    @Override
    public Class<TypeDeleteRequest> getRequestClass() {
        return TypeDeleteRequest.class;
    }
}
