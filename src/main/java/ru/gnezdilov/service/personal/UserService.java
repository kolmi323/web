package ru.gnezdilov.service.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.dto.UserDTO;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConverterUserModelToUserDTO converter;

    public UserDTO getUserById(int id) {
        return converter.convert(userRepository.getOne(id));
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }
}
