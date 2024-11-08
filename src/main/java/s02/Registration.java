package s02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registration {
    private Connection con;
    private DigestService digestService;

    public Registration(Connection con) {
        this.con = con;
        this.digestService = new MD5DigestUtils();
    }

    public void register() {
        UserDataReceiver userDataReceiver = new UserDataReceiver(this.con);
        try (PreparedStatement ps = this.con.prepareStatement(
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"
        )) {
            String name = userDataReceiver.enterName();
            String email = userDataReceiver.enterEmail();
            String password = this.digestService.hashPassword(userDataReceiver.enterPassword());
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
