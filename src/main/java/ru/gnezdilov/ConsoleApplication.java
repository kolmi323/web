package ru.gnezdilov;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import ru.gnezdilov.view.auth.LoginPage;

@SpringBootApplication
public class ConsoleApplication {
    public static void main(String[] args) {
        System.out.println("Console Application started");
        SpringApplication.run(ConsoleApplication.class, "java -jar app.jar --spring.profiles.active=console\n");
        System.out.println("----------------------------");
        System.out.println("\nConsole Application finished");
    }
}
