package ru.gnezdilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import ru.gnezdilov.config.ApplicationRunConfiguration;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        System.out.println("Web Application Started");
        SpringApplication.run(WebApplication.class, args);
        System.out.println("----------------------------");
        System.out.println("\nServer Started");
    }
}
