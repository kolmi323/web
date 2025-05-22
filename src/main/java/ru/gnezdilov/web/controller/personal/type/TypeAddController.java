package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.type.add.TypeAddRequest;
import ru.gnezdilov.web.json.type.add.TypeAddResponse;

@Service("/type/add")
@RequiredArgsConstructor
public class TypeAddController implements SecureController<TypeAddRequest, TypeAddResponse> {
    private final TypeService typeService;

    @Override
    public TypeAddResponse handle(TypeAddRequest request, int userId) {
        TypeDTO typeDTO = typeService.create(userId, request.getName());
        if (typeDTO != null) {
            return new TypeAddResponse(typeDTO.getId(), typeDTO.getName());
        }
        return null;
    }

    @Override
    public Class<TypeAddRequest> getRequestClass() {
        return TypeAddRequest.class;
    }
}
