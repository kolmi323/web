package ru.gnezdilov.web.controller.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.UIUtils;
import ru.gnezdilov.web.abstractcustom.AbstractController;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;

@Service("/login")
public class AuthController extends AbstractController<AuthRequest, AuthResponse>{
    private final AuthService authService;

    public AuthController(AuthService authService, ObjectMapper om, UIUtils utils) {
        super(om, utils);
        this.authService = authService;
    }

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
