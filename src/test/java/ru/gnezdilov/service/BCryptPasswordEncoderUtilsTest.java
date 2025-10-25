package ru.gnezdilov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
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