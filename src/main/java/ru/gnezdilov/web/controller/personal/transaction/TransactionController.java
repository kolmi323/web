package ru.gnezdilov.web.controller.personal.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;
import ru.gnezdilov.web.interfaces.InformationController;
import ru.gnezdilov.web.interfaces.SecureController;
import ru.gnezdilov.web.json.navigation.NavigationRequest;
import ru.gnezdilov.web.json.navigation.NavigationResponse;

@Service("/transaction")
@RequiredArgsConstructor
public class TransactionController implements InformationController<NavigationResponse> {
    private final UserService userService;

    @Override
    public NavigationResponse handle(int userId) {
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO != null) {
            String[] navigationItems = {"/add"};
            return new NavigationResponse("transaction page", userDTO.getName(), navigationItems);
        }
        return null;
    }
}
