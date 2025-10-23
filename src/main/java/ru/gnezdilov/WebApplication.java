package ru.gnezdilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        System.out.println("Web Application Started");
        SpringApplication.run(WebApplication.class, args);
        System.out.println("----------------------------");
        System.out.println("\nServer Started\n");
        System.out.println("----------------------------");
    }
}
