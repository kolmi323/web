package ru.gnezdilov.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.api.json.auth.AuthResponse;

@Service
public class ConverterUserDTOToAuthResponse implements Converter<UserDTO, AuthResponse> {
    @Override
    public AuthResponse convert(UserDTO source) {
        return new AuthResponse(source.getId(), source.getEmail());
    }
}
