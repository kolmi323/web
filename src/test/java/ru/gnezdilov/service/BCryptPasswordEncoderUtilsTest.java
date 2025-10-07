package ru.gnezdilov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BCryptPasswordEncoderUtilsTest {
    @InjectMocks private BCryptPasswordEncoderUtils subj;

    @Test
    public void hashPassword_returnHash_whenPasswordIsValid() {
        String password = "password";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(password, subj.hashPassword(password)));
    }

    @Test
    public void hashPassword_throwNullPointerException_whenPasswordIsNull() {
        String password = null;
        assertThrows(NullPointerException.class, () -> subj.hashPassword(password));
    }
}