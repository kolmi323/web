package ru.gnezdilov.web.controller.personal.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.controller.interfaces.SecureController;
import ru.gnezdilov.web.json.navigation.NavigationRequest;
import ru.gnezdilov.web.json.navigation.NavigationResponse;

@Service("/type")
@RequiredArgsConstructor
public class TypeController implements SecureController<NavigationRequest, NavigationResponse> {
    private final UserService userService;

    @Override
    public NavigationResponse handle(NavigationRequest request, int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO != null) {
            String[] navigationItems = {"/show", "/update", "/add", "/delete"};
            return new NavigationResponse("type page", userDTO.getName(), navigationItems);
        }
        return null;
    }

    @Override
    public Class<NavigationRequest> getRequestClass() {
        return NavigationRequest.class;
    }
}
