package ru.gnezdilov.view;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Login Page");
        ViewFactory.getInstance().getLoginPage().start();
        System.out.println(("You exit from site. Good luck!"));
    }
}
