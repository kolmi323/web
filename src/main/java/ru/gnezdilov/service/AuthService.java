package ru.gnezdilov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.security.UserRole;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.dto.UserDTO;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoderUtils digestService;
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
        linkUserRole(user);
        return converter.convert(userRepository.save(user));
    }

    private void linkUserRole(UserModel user) {
        Set<UserRole> userRoles = user.getRoles();
        userRoles.add(UserRole.USER);
        user.setRoles(userRoles);
    }
}
