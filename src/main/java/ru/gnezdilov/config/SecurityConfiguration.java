package ru.gnezdilov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gnezdilov.security.CustomUserDetailsService;

import static ru.gnezdilov.security.UserRole.ADMIN;
import static ru.gnezdilov.security.UserRole.USER;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/start", "/login-form", "/register", "/alertError", "/alertMessage", "/api/register")
                    .permitAll()
                .anyRequest()
                    .hasAnyRole(USER.name(), ADMIN.name())
                .and()
                    .formLogin()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/login-form")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/personal", true)
                .and()
                    .sessionManagement()
                        .sessionFixation()
                        .newSession()
                .and()
                    .httpBasic()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/start")
                        .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
