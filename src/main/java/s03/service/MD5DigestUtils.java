package s03.service;

import org.apache.commons.codec.digest.DigestUtils;
import s03.service.CustomInterface.DigestService;

public class MD5DigestUtils implements DigestService {
    @Override
    public String hashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
