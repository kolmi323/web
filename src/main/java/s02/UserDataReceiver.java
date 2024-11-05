package s02;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class UserDataReceiver {
    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private InputRequest inputRequest = new InputRequest();

    public UserDataReceiver(Connection con) {
        this.con = con;
    }

    public String enterName() throws ActionUserExitException {
        String name;
        while (true) {
            name = this.inputRequest.requestStr("Enter your Name or enter \"exit\" to exit: ");
            if (name.equals("exit")) {
                throw new ActionUserExitException("You canceled the name entry");
            } else if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else {
                return name;
            }
        }
    }

    public String enterPassword() throws ActionUserExitException {
        String password;
        while (true) {
            password = this.inputRequest.requestStr("Enter your Password or enter \"exit\" to exit: ");
            if (password.equals("exit")) {
                throw new ActionUserExitException("You canceled the password entry");
            } else if (password.isEmpty()) {
                System.out.println("Password cannot be empty");
            }
            else if (password.length() > 10) {
                System.out.println("Password too long");
            } else {
                return password;
            }
        }
    }

    public String enterEmail() throws ActionUserExitException {
        String email;
        while (true) {
            email = this.inputRequest.requestStr("Enter your Email or enter \"exit\" to exit: ").toLowerCase();
            if (email.equals("exit")) {
                throw new ActionUserExitException("You canceled the email entry");
            } else if (email.isEmpty()) {
                System.out.println("Email cannot be empty");
            }
            else if (isEmailCorrect(email)) {
                return email;
            }
        }
    }

    public String enterNameAccount(int userId) throws ActionUserExitException {
        String name;
        while (true) {
            name = this.inputRequest.requestStr("Enter name for your account or enter \"exit\" to exit: ");
            if (name.equals("exit")) {
                throw new ActionUserExitException("You canceled the email entry");
            } else if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else if (!containsNameAccount(name, userId)) {
                return name;
            }
        }
    }

    public BigDecimal enterBalanceAccount() throws ActionUserExitException{
        String answer;
        while (true) {
            answer = this.inputRequest.requestStr("Enter balance for your account or enter \"exit\" to exit: ");
            if (answer.equals("exit")) {
                throw new ActionUserExitException("You canceled the balance entry");
            } else if (answer.isEmpty()) {
                System.out.println("Balance cannot be empty");
            } else if (Pattern.matches("^[0-9]+\\.[0-9]+$", answer)) {
                return  new BigDecimal(answer).setScale(2, RoundingMode.HALF_UP);
            } else {
                System.out.println(
                        "Balance in not valid" +
                                "\nBalance example: 12345.12345"
                );
            }
        }
    }

    private boolean containsNameAccount(String name, int id) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT a.id, a.name from account as a");
            while (rs.next()) {
                if (rs.getString("name").equals(name) && rs.getInt("id") == id) {
                    System.out.println("Name already exists");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean containsEmail(String email) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT u.email from users as u");
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    System.out.println("Email already exists");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean isEmailCorrect(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9_.-]+)@([a-z0-9_.-]+)\\.([a-z.]{2,6})$", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(email).matches()) {
            System.out.println(
                    "Email is not valid" +
                            "\nEmail example: vasnecov@yandex.ru"
                    );
            return false;
        }
        return !containsEmail(email);
    }
}
