package s02;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
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
        try {
            String name = userDataReceiver.enterName();
            String email = userDataReceiver.enterEmail();
            MD5DigestUtils md5 = new MD5DigestUtils();
            String password = md5.hashPassword(userDataReceiver.enterPassword());
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.execute();
            System.out.println("Registration Successful");
        } catch (ActionUserExitException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
