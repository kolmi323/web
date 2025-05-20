package ru.gnezdilov.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.controller.Controller;
import ru.gnezdilov.web.json.register.RegisterRequest;
import ru.gnezdilov.web.json.register.RegisterResponse;

@Service("/register")
@RequiredArgsConstructor
public class RegisterController implements Controller<RegisterRequest, RegisterResponse> {
    private final AuthService authService;

    @Override
    public RegisterResponse handle(RegisterRequest request) {
        UserDTO userDTO = authService.createNewUser(request.getName(), request.getEmail(), request.getPassword());
        if (userDTO != null) {
            return new RegisterResponse(userDTO.getId(), userDTO.getEmail());
        }
        return null;
    }

    @Override
    public Class<RegisterRequest> getRequestClass() {
        return RegisterRequest.class;
    }
}
