package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.abstractcustom.AbstractController;
import ru.gnezdilov.web.interfaces.Controller;
import ru.gnezdilov.web.json.register.RegisterRequest;
import ru.gnezdilov.web.json.register.RegisterResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service("/register")
@RequiredArgsConstructor
public class RegisterController extends AbstractController<RegisterRequest, RegisterResponse> {
    private final AuthService authService;

    @Override
    public RegisterResponse handle(RegisterRequest request) {
        UserDTO userDTO = authService.createNewUser(
                request.getName(),
                this.extractEmail(request.getEmail()),
                this.extractPassword(request.getPassword())
        );
        return new RegisterResponse(userDTO.getId(), userDTO.getEmail());
    }

    @Override
    public Class<RegisterRequest> getRequestClass() {
        return RegisterRequest.class;
    }

    @Override
    protected RegisterResponse wrapHandle(HttpServletRequest req) throws IOException {
        return handle(this.om.readValue(req.getInputStream(), getRequestClass()));
    }
}
