package ru.gnezdilov;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.security.CustomUserDetailsService;

import static java.util.Collections.singleton;
import static org.mockito.Mockito.when;
import static ru.gnezdilov.security.UserRole.USER;

@Configuration
public class MockSecurityConfiguration {
    @MockBean
    UserRepository userRepository;

    @Bean("customTestUserDetailsService")
    @Scope("prototype")
    public CustomUserDetailsService customUserDetailsService() {
        UserModel user = new UserModel(
                1,
                "John",
                "john@mail.ru",
                "$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO"
        );
        user.setRoles(singleton(USER));

        when(userRepository.findByEmail("john@mail.ru")).thenReturn(user);

        return new CustomUserDetailsService(userRepository);
    }
}
