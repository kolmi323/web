package ru.gnezdilov.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.gnezdilov.view.auth.LoginPage;

@Configuration
public class ApplicationRunConfiguration {
    @Bean
    @Profile("console")
    public CommandLineRunner commandLineRunner(LoginPage loginPage) {
        return args -> {
            System.out.println("Welcome to the Login Page");
            loginPage.start();
            System.out.println(("You exit from site. Good luck!"));
        };
    }
}
