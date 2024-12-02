package s03.converter;

import s03.dao.UserModel;
import s03.service.UserDTO;

public class ConverterUserModelToUserDTO implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        return new UserDTO(source.getId() ,source.getName(), source.getPassword());
    }
}
