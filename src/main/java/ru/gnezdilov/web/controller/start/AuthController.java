package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.interfaces.Controller;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;

@Service("/login")
@RequiredArgsConstructor
public class AuthController implements Controller<AuthRequest, AuthResponse> {
    private final AuthService authService;

    @Override
    public AuthResponse handle(AuthRequest request) {
        UserDTO userDTO = authService.authorization(request.getEmail(), request.getPassword());
        if (userDTO != null) {
            return new AuthResponse(userDTO.getId(), userDTO.getEmail());
        }
        return null;
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }
}
