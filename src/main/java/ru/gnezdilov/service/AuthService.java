package ru.gnezdilov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.dto.UserDTO;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final DigestService digestService;
    private final ConverterUserModelToUserDTO converter;

    public UserDTO authorization(String email, String password) {
        return userRepository.findByEmailAndPassword(email, digestService.hashPassword(password))
                .map(converter::convert)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public UserDTO createNewUser (String name, String email, String password) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(digestService.hashPassword(password));
        return converter.convert(userRepository.save(user));
    }
}
