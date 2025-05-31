package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.abstractcustom.AbstractSecureController;
import ru.gnezdilov.web.json.DeleteResponse;
import ru.gnezdilov.web.json.type.delete.TypeDeleteRequest;

@Service("/type/delete")
@RequiredArgsConstructor
public class TypeDeleteController extends AbstractSecureController<TypeDeleteRequest, DeleteResponse> {
    private final TypeService typeService;

    @Override
    public DeleteResponse handle(TypeDeleteRequest request, int userId) {
        boolean isDelete = typeService.delete(request.getId(), userId);
        return new DeleteResponse(isDelete);
    }

    @Override
    public Class<TypeDeleteRequest> getRequestClass() {
        return TypeDeleteRequest.class;
    }
}
