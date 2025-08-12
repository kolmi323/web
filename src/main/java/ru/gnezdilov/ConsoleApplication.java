package ru.gnezdilov;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.view.auth.LoginPage;

@SpringBootApplication
@AllArgsConstructor
public class ConsoleApplication implements CommandLineRunner {
    private final LoginPage loginPage;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the Login Page");
        loginPage.start();
        System.out.println(("You exit from site. Good luck!"));
    }
}
