package ru.gnezdilov.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.custominterface.DigestService;

@Service
public class BCryptPasswordEncoderUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password is null");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
