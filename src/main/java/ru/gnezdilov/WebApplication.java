package ru.gnezdilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ru.gnezdilov.security.UserRole.USER;
import static ru.gnezdilov.security.UserRole.ADMIN;

@SpringBootApplication
public class WebApplication extends WebSecurityConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/start", "/login-form", "/register", "/alertError", "/alertMessage", "/api/register")
                        .permitAll()
                    .antMatchers("/**").hasAnyRole(USER.name(), ADMIN.name())
                .and()
                    .formLogin()
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/login-form")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/personal")
                .and()
                    .httpBasic()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/start");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
