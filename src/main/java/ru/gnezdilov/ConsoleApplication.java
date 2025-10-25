package ru.gnezdilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsoleApplication {
    public static void main(String[] args) {
        System.out.println("Console Application started");
        SpringApplication.run(ConsoleApplication.class, "java -jar app.jar --spring.profiles.active=console\n");
        System.out.println("----------------------------");
        System.out.println("\nConsole Application finished");
    }
}
