package ru.gnezdilov.view;

import org.springframework.stereotype.Component;
import ru.gnezdilov.service.dto.UserDTO;

@Component
public class CurrentUser {
    private UserDTO userDTO;

    public UserDTO getCurrentUser() {
        return userDTO;
    }

    public void setCurrentUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
