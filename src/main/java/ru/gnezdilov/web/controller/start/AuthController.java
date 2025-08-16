package ru.gnezdilov.web.controller.start;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.converter.ConverterUserDTOToAuthResponse;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.web.json.auth.AuthRequest;
import ru.gnezdilov.web.json.auth.AuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final ConverterUserDTOToAuthResponse converter;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<AuthResponse> auth(@RequestBody @Valid AuthRequest authRequest,
                                                           HttpServletRequest httpServletRequest) {
        UserDTO user = authService.authorization(authRequest.getEmail(), authRequest.getPassword());

        if (user == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", user.getId());

        return ok(Objects.requireNonNull(converter.convert(user)));
    }
}
