package ru.gnezdilov.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.MainConfiguration;
import ru.gnezdilov.view.auth.LoginPage;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        System.out.println("Welcome to the Login Page");
        LoginPage loginPage = context.getBean(LoginPage.class);
        loginPage.start();
        System.out.println(("You exit from site. Good luck!"));
    }
}
