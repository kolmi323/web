package ru.gnezdilov.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class CustomUserDetailsServiceTest {
    @Autowired
    CustomUserDetailsService subj;

    @MockBean
    UserRepository userRepository;

    @Before
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