package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.dao.model.UserModel;

@Service
public class ConverterUserModelToUserDTO implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        if (source == null) {
            return null;
        }
        return new UserDTO(source.getId() ,source.getName(), source.getEmail());
    }
}
