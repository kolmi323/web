package web.converter;

import web.dao.Model.UserModel;
import web.service.DTO.UserDTO;

public class ConverterUserModelToUserDTO implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        return new UserDTO(source.getId() ,source.getName(), source.getEmail());
    }
}
