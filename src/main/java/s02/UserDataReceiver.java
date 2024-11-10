package s02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class UserDataReceiver {
    private Connection con;
    private ResultSet rs;
    private InputRequest inputRequest;

    public UserDataReceiver(Connection con) {
        this.con = con;
        this.inputRequest = new InputRequest();
    }

    public String enterName() {
        return this.inputRequest.requestNotBlancString("Enter your Name or enter \"exit\" to exit: ");
    }

    public String enterPassword() {
        Predicate<String> predicate = str -> str.length() <= 10;
        return this.inputRequest.requestNotBlancString("Enter your Password or enter \"exit\" to exit: ", predicate);
    }

    public String enterEmail(boolean isEmailForRegistration) {
        Predicate<String> predicate;
        if (isEmailForRegistration) {
            predicate = str -> isEmailCorrect(str) && !containsEmail(str);
        } else {
            predicate = str -> isEmailCorrect(str);
        }
        return this.inputRequest.requestNotBlancString("Enter your Email or enter \"exit\" to exit: ", predicate);
    }

    public String enterNameAccount(int userId) {
        Predicate<String> predicate = str -> !containsNameAccount(str, userId);
        return this.inputRequest.requestNotBlancString("Enter your name for your account " +
                "or enter \"exit\" to exit: ", predicate);
    }

    public String enterBalanceAccount() {
        Predicate<String> predicate = str -> isBalanceAccountCorrect(str);
        return this.inputRequest.requestNotBlancString("Enter balance for your account " +
                "or enter \"exit\" to exit: ", predicate);
    }

    private boolean containsNameAccount(String name, int id) {
        try (Statement st = con.createStatement()){
            rs = st.executeQuery("SELECT a.name from account as a where a.user_id = " + id);
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
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
        try (Statement st = con.createStatement()){
            rs = st.executeQuery("SELECT u.email from users as u");
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
            System.out.println("Email is not valid" +
                            "\nEmail example: vasnecov@yandex.ru");
            return false;
        }
        return true;
    }

    private boolean isBalanceAccountCorrect(String balance) {
        Pattern pattern = Pattern.compile("^[0-9]+\\.[0-9]+$", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(balance).matches()) {
            System.out.println("Balance in not valid" +
                    "\nBalance example: 12345.12345");
            return false;
        }
        return true;
    }
}
