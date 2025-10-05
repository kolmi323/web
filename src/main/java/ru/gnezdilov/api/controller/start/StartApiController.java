package ru.gnezdilov.api.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.converter.ConverterUserDTOToAuthResponse;
import ru.gnezdilov.api.converter.ConverterUserDTOToRegisterResponse;
import ru.gnezdilov.api.json.register.RegisterRequest;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StartApiController extends AbstractController {
    private final AuthService authService;
    private final UserService userService;
    private final ConverterUserDTOToRegisterResponse converterUserDTOToRegisterResponse;
    private final ConverterUserDTOToAuthResponse converterUserDTOToAuthResponse;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserDTO user = authService.createNewUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
        return converterUserDTOToRegisterResponse.convert(user);
    }
}
