package ru.gnezdilov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import static java.util.Collections.singleton;
import static ru.gnezdilov.security.UserRole.USER;

@Configuration
public class MockSecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return user -> new CustomUserDetails(
                1,
                "john@mail.ru",
                "$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO",
                singleton(new CustomGrantedAuthority(USER))
        );
    }
}
