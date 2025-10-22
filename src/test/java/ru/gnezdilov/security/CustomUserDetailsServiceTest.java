package ru.gnezdilov.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.entities.UserModel;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = WebApplication.class)
public class CustomUserDetailsServiceTest {
    @Autowired
    CustomUserDetailsService subj;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        UserModel user = new UserModel(
                1,
                "John",
                "john@mail.ru",
                "$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO"
        );

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);
        user.setRoles(userRoles);

        when(userRepository.findByEmail("john@mail.ru"))
                .thenReturn(user);
    }

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = subj.loadUserByUsername( "john@mail.ru");
        assertNotNull(userDetails);
        assertEquals("john@mail.ru", userDetails.getUsername());
        assertEquals("$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals(new CustomGrantedAuthority(UserRole.USER), userDetails.getAuthorities().iterator().next());
    }
}