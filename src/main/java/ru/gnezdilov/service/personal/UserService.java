package ru.gnezdilov.service.personal;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.dto.UserDTO;

@Service
public class UserService {
    private UserRepository userRepository;
    private ConverterUserModelToUserDTO converter;

    public UserService(UserRepository userRepository, ConverterUserModelToUserDTO converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    public UserDTO getUserById(int id) {
        return converter.convert(userRepository.getOne(id));
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }
}
