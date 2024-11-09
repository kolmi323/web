package s02;

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

    public String requestNotBluncString(String text) {
        while (true) {
            String answer = this.requestStr(text);
            if (answer.equalsIgnoreCase("exit")) {
                System.out.println(("You canceled the Answer entry"));
                return "exit";
            } else if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty");
            } else {
                return answer;
            }
        }
    }

    public String requestNotBluncString(String text, Predicate<String> predicate) {
        while (true) {
            String answer = this.requestStr(text);
            if (answer.equalsIgnoreCase("exit")) {
                System.out.println(("You canceled the Answer entry"));
                return "exit";
            } else if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty");
            } else if (predicate.test(answer)) {
                return answer;
            }
        }
    }
}
