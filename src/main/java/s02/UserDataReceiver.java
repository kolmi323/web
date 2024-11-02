package s02;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDataReceiver {
    private Connection con;
    private ResultSet rs;
    private Statement stmt;
    private InputRequest inputRequest = new InputRequest();

    public UserDataReceiver(Connection con) {
        this.con = con;
    }

    public String enterName() {
        String name;
        while (true) {
            name = this.inputRequest.requestStr("Enter your Name or enter \"exit\" to exit: ");
            if (name.equals("exit")) {
                break;
            } else if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else {
                return name;
            }
        }
        return null;
    }

    public String enterPassword() {
        String password;
        while (true) {
            password = this.inputRequest.requestStr("Enter your Password or enter \"exit\" to exit: ");
            if (password.equals("exit")) {
                break;
            } else if (password.length() > 10) {
                System.out.println("Password too long");
            } else {
                return password;
            }
        }
        return null;
    }

    public String enterEmail() {
        String email;
        while (true) {
            email = this.inputRequest.requestStr("Enter your Email or enter \"exit\" to exit: ").toLowerCase();
            if (email.equals("exit")) {
                break;
            } else if (!containsEmail(email)) {
                return email;
            }
        }
        return null;
    }

    public String enterNameAccount() {
        String name;
        while (true) {
            name = this.inputRequest.requestStr("Enter name for your account or enter \"exit\" to exit: ");
            if (name.equals("exit")) {
                break;
            } else if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else if (!containsNameAccount(name)) {
                return name;
            }
        }
        return null;
    }

    public double enterBalanceAccount() {
        String balance;
        while (true) {
            balance = this.inputRequest.requestStr("Enter balance for your account or enter \"exit\" to exit: ");
            if (balance.equals("exit")) {
                break;
            } else if (balance.isEmpty()) {
                System.out.println("Name cannot be empty");
            } else if (balance.contains(",")) {
                System.out.println("Balance contains invalid characters");
            } else {
                return (double) Math.round(Double.parseDouble(balance) * 100.0) / 100.0;
            }
        }
        return 0.00;
    }

    private boolean containsNameAccount(String name) {
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT a.name from account as a");
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
}
