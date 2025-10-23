package ru.gnezdilov.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import ru.gnezdilov.WebApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = WebApplication.class)
public class CustomUserDetailsServiceTest {
    @Autowired
    @Qualifier("customTestUserDetailsService")
    CustomUserDetailsService subj;

    @Test
    public void loadUserByUsername_returnCorrectUserDetails_whenCalledWithValidUsername() {
        UserDetails userDetails = subj.loadUserByUsername( "john@mail.ru");
        assertNotNull(userDetails);
        assertEquals("john@mail.ru", userDetails.getUsername());
        assertEquals("$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(new CustomGrantedAuthority(UserRole.USER), userDetails.getAuthorities().iterator().next());
    }

    @Test
    public void loadUserByUsername_returnUserNameNotFoundException_whenUsernameNotFound() {
        assertThrows(UsernameNotFoundException.class, () -> subj.loadUserByUsername( "artur@mail.ru"));
    }
}