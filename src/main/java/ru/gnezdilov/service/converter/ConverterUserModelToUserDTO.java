package ru.gnezdilov.service.converter;

import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.dao.model.UserModel;

public class ConverterUserModelToUserDTO implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        return new UserDTO(source.getId() ,source.getName(), source.getEmail());
    }
}
