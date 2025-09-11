package ru.gnezdilov.api.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gnezdilov.api.ApiController;
import ru.gnezdilov.api.converter.ConverterUserDTOToRegisterResponse;
import ru.gnezdilov.api.json.register.RegisterRequest;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
public class RegisterApiController extends ApiController {
    private final AuthService authService;
    private final ConverterUserDTOToRegisterResponse converter;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest,
                                                                   HttpServletRequest httpServletRequest) {
        UserDTO user = authService.createNewUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
        if (user == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        this.putUserIdToSession(httpServletRequest, user.getId());
        return ok(Objects.requireNonNull(converter.convert(user)));
    }
}
