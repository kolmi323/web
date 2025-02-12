package ru.gnezdilov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MD5DigestUtilsTest {
    @InjectMocks private MD5DigestUtils subj;

    @Test
    public void hashPassword_returnHash_whenPasswordIsValid() {
        String password = "password";
        String hash = "5f4dcc3b5aa765d61d8327deb882cf99";

        assertEquals(hash, subj.hashPassword(password));
    }

    @Test
    public void hashPassword_throwNullPointerException_whenPasswordIsNull() {
        String password = null;
        assertThrows(NullPointerException.class, () -> subj.hashPassword(password));
    }
}