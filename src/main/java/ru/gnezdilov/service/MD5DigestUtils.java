package ru.gnezdilov.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.custominterface.DigestService;

@Service
public class MD5DigestUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password is null");
        }
        return DigestUtils.md5Hex(password);
    }
}
