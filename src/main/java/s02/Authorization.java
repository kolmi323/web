package s02;

import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;

import java.sql.*;

public class Authorization {
    private Connection con;
    private int userId;
    private DigestService digestService;
    private ControlExit controlExit;

    public Authorization(Connection con) {
        this.con = con;
        this.digestService = new MD5DigestUtils();
        this.controlExit = new ControlExit();
    }

    public void logIn() {
        UserDataReceiver userDataReceiver = new UserDataReceiver(this.con);
        try (PreparedStatement st = this.con.prepareStatement(
                "select u.id, u.name, u.password from users as u where u.name = ? and u.email = ? and u.password = ?"
        )) {
            String name = userDataReceiver.enterName();
            if (controlExit.isExit(name)) {
                return;
            }
            String email = userDataReceiver.enterEmail(false);
            if (controlExit.isExit(email)) {
                return;
            }
            String password = userDataReceiver.enterPassword();
            if (controlExit.isExit(password)) {
                return;
            }
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, this.digestService.hashPassword(password));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
                System.out.println("Logged in");
            } else {
                System.out.println("You are not logged in");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserId() {
        return userId;
    }
}
