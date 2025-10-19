package ru.gnezdilov.api.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.converter.ConverterUserDTOToRegisterResponse;
import ru.gnezdilov.api.json.register.RegisterRequest;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StartApiController extends AbstractController {
    private final AuthService authService;
    private final ConverterUserDTOToRegisterResponse converterUserDTOToRegisterResponse;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserDTO user = authService.createNewUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
        return converterUserDTOToRegisterResponse.convert(user);
    }
}
