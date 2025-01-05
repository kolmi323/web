package ru.gnezdilov.service;

import org.apache.commons.codec.digest.DigestUtils;
import ru.gnezdilov.service.custominterface.DigestService;

public class MD5DigestUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
