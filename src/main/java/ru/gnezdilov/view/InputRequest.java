package ru.gnezdilov.view;

import ru.gnezdilov.dao.exception.ExitException;

import java.util.Scanner;
import java.util.function.Predicate;

public class InputRequest {
    private final Scanner scanner;


    public InputRequest() {
        this.scanner = new Scanner(System.in);
    }

    public String requestStr(String text) {
        System.out.println(text);
        return this.scanner.nextLine();
    }

    public String requestNotBlancString(String text) {
        while (true) {
            String answer = this.requestStr(text);
            if (answer.equalsIgnoreCase("exit")) {
                throw new ExitException();
            } else if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty");
            } else {
                return answer;
            }
        }
    }

    public String requestNotBlancString(String text, Predicate<String> predicate) {
        while (true) {
            String answer = this.requestStr(text);
            if (answer.equalsIgnoreCase("exit")) {
                throw new ExitException();
            } else if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty");
            } else if (predicate.test(answer)) {
                return answer;
            }
        }
    }
}
