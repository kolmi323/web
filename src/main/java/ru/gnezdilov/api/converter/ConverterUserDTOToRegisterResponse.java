package ru.gnezdilov.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.api.json.register.RegisterResponse;

@Service
public class ConverterUserDTOToRegisterResponse implements Converter<UserDTO, RegisterResponse> {
    @Override
    public RegisterResponse convert(UserDTO source) {
        return new RegisterResponse(source.getId(), source.getEmail());
    }
}
