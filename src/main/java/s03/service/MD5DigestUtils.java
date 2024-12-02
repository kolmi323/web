package s03.service;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5DigestUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
