package web.service;

import org.apache.commons.codec.digest.DigestUtils;
import web.service.CustomInterface.DigestService;

public class MD5DigestUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
