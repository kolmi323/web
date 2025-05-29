package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.abstractcustom.AbstractController;
import ru.gnezdilov.web.interfaces.Controller;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service("/login")
@RequiredArgsConstructor
public class AuthController extends AbstractController<AuthRequest, AuthResponse>{
    private final AuthService authService;

    @Override
    public AuthResponse handle(AuthRequest request) {
        UserDTO userDTO = authService.authorization(
                this.extractEmail(request.getEmail()),
                this.extractPassword(request.getPassword())
        );
        return new AuthResponse(userDTO.getId(), userDTO.getEmail());
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }
}
