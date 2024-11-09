package s02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registration {
    private Connection con;
    private DigestService digestService;
    private ControlExit controlExit;

    public Registration(Connection con) {
        this.con = con;
        this.digestService = new MD5DigestUtils();
        this.controlExit = new ControlExit();
    }

    public void register() {
        UserDataReceiver userDataReceiver = new UserDataReceiver(this.con);
        try (PreparedStatement ps = this.con.prepareStatement(
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"
        )) {
            String name = userDataReceiver.enterName();
            if (controlExit.isExit(name)) {
                return;
            }
            String email = userDataReceiver.enterEmail(true);
            if (controlExit.isExit(email)) {
                return;
            }
            String password = userDataReceiver.enterPassword();
            if (controlExit.isExit(password)) {
                return;
            }
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, this.digestService.hashPassword(password));
            if (ps.executeUpdate() > 0) {
                System.out.println("Registration Successful");
            } else {
                throw new SQLException("Registration Failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
