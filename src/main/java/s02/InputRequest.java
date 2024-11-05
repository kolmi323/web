package s02;

import java.util.Scanner;

public class InputRequest {
    private Scanner scanner = new Scanner(System.in);

    public String requestStr(String text) {
        System.out.println(text);
        return this.scanner.nextLine();
    }
}
