package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import org.springframework.core.convert.converter.Converter;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.dao.entities.UserModel;

@Service
public class ConverterUserModelToUserDTO implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        return new UserDTO(source.getId() ,source.getName(), source.getEmail());
    }
}
