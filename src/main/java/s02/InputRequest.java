package s02;

import java.util.Scanner;

public class InputRequest {
    private final Scanner scanner;

    public InputRequest() {
        this.scanner = new Scanner(System.in);
    }

    public String requestStr(String text) {
        System.out.println(text);
        return this.scanner.nextLine();
    }
}
