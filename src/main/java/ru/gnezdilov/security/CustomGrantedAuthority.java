package ru.gnezdilov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private final String prefix = "ROLE_";
    private final UserRole userRole;

    @Override
    public String getAuthority() {
        return prefix + userRole.toString();
    }
}
