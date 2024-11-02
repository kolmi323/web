package s02;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registration {
    private Connection con;

    public Registration(Connection con) {
        this.con = con;
    }

    public void register() {
        UserDataReceiver userDataReceiver = new UserDataReceiver(this.con);
        String name = userDataReceiver.enterName();
        String email = userDataReceiver.enterEmail();
        String password = userDataReceiver.enterPassword();
        try {
            if (containsNullInUserData(name, email, password)) {
                return;
            }
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, DigestUtils.md2Hex(password));
            ps.execute();
            System.out.println("Registration Successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean containsNullInUserData(String name, String email, String password) {
        if (name == null) {
            System.out.println("Name cannot be null");
            return true;
        } else if (email == null) {
            System.out.println("Email cannot be null");
            return true;
        } else if (password == null) {
            System.out.println("Password cannot be null");
            return true;
        } else {
            return false;
        }
    }
}
