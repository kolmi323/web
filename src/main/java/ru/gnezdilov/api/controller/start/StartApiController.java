package ru.gnezdilov.api.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.converter.ConverterUserDTOToAuthResponse;
import ru.gnezdilov.api.converter.ConverterUserDTOToRegisterResponse;
import ru.gnezdilov.api.json.auth.AuthRequest;
import ru.gnezdilov.api.json.auth.AuthResponse;
import ru.gnezdilov.api.json.register.RegisterRequest;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StartApiController extends AbstractController {
    private final AuthService authService;
    private final ConverterUserDTOToRegisterResponse converterUserDTOToRegisterResponse;
    private final ConverterUserDTOToAuthResponse converterUserDTOToAuthResponse;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest,
                                                                   HttpServletRequest httpServletRequest) {
        UserDTO user = authService.createNewUser(registerRequest.getName(), registerRequest.getEmail(), registerRequest.getPassword());
        this.handleUser(user);
        this.wrapUserId(httpServletRequest, user.getId());
        return converterUserDTOToRegisterResponse.convert(user);
    }

    @PostMapping("/login")
    public AuthResponse auth(@RequestBody @Valid AuthRequest authRequest,
                                             HttpServletRequest httpServletRequest) {
        UserDTO user = authService.authorization(authRequest.getEmail(), authRequest.getPassword());
        this.handleUser(user);
        this.wrapUserId(httpServletRequest, user.getId());
        return converterUserDTOToAuthResponse.convert(user);
    }
}
