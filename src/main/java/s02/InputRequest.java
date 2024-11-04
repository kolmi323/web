package s02;

import java.util.Scanner;

public class InputRequest {
    private Scanner scanner = new Scanner(System.in);

    public String requestStr(String text) {
        System.out.println(text);
        return this.scanner.nextLine();
    }

    public int requestInt(String text) {
        System.out.println(text);
        return this.scanner.nextInt();
    }

    public double requestDouble(String text) {
        System.out.println(text);
        return this.scanner.nextDouble();
    }
}
