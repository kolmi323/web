package ru.gnezdilov.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UIUtils {
    private final InputRequest inputRequest;

    public UIUtils() {
        inputRequest = new InputRequest();
    }

    public boolean isExitAction(String text) {
        return text.equalsIgnoreCase("exit");
    }

    public boolean isEmailCorrect(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9_.-]+)@([a-z0-9_.-]+)\\.([a-z.]{2,6})$",
                Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(email).matches()) {
            return true;
        }
        System.out.println("Email is not valid" +
                "\nEmail example: vasnecov@yandex.ru");
        return false;
    }

    public boolean isPasswordCorrect(String password) {
        if (password.length() < 10) {
            return true;
        }
        System.out.println("Password length is too long");
        return false;
    }

    public boolean isBalanceAccountCorrect(String balance) {
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]{2}$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(balance).matches()) {
            return true;
        }
        System.out.println("Balance in not valid" +
                "\nBalance example: 12345.12");
        return false;
    }

    public boolean isDateCorrect(String data) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            df.setLenient(false);
            df.parse(data);
        } catch (ParseException e) {
            System.out.println("Data is not valid" +
                    "\nData example: 2024-12-31");
            return false;
        }
        return true;
    }

    public String enterName() {
        String message = "Enter your Name or enter \"exit\" to exit: ";
        return this.inputRequest.requestNotBlancString(message);
    }

    public String enterEmail() {
        String message = "Enter your Email or enter \"exit\" to exit: ";
        Predicate<String> predicate;
        predicate = this::isEmailCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }

    public String enterPassword() {
        String message = "Enter your Password or enter \"exit\" to exit: ";
        Predicate<String> predicate = this::isPasswordCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }

    public String enterNameAccount() {
        String message = "Enter your Name Account or enter \"exit\" to exit: ";
        return this.inputRequest.requestNotBlancString(message);
    }

    public String enterBalanceAccount() {
        String message = "Enter your Balance for your account or enter \"exit\" to exit: ";
        Predicate<String> predicate = this::isBalanceAccountCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }

    public String enterNameType(boolean isNewName) {
        String message = "Enter your Name Type or enter \"exit\" to exit: ";
        if (isNewName) {
            message = "Enter your New Name Type or enter \"exit\" to exit: ";
        }
        return this.inputRequest.requestNotBlancString(message);
    }

    public String enterId() {
        String message = "Enter your ID or enter \"exit\" to exit: ";
        return this.inputRequest.requestNotBlancString(message);
    }

    public String enterDate() {
        String message = "Enter your Date or enter \"exit\" to exit: ";
        Predicate<String> predicate = this::isDateCorrect;
        return this.inputRequest.requestNotBlancString(message, predicate);
    }
}
