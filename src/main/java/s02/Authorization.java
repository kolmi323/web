package s02;

import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Authorization {
    private Connection con;
    int userId;

    public Authorization(Connection con) {
        this.con = con;
    }

    public void logIn() {
        UserDataReceiver userDataReceiver = new UserDataReceiver(this.con);
        try {
            String name = userDataReceiver.enterName();
            MD5DigestUtils md5 = new MD5DigestUtils();
            String password = md5.hashPassword(userDataReceiver.enterPassword());
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery("select u.id, u.name, u.password from users as u");
            while (rs.next()) {
                if (
                        name.equals(rs.getString("name")) &&
                        password.equals(rs.getString("password"))
                ) {
                    System.out.println("Logged in");
                    this.userId = rs.getInt("id");
                    return;
                }
            }
            System.out.println("You are not logged in");
        } catch (ActionUserExitException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getUserId() {
        return userId;
    }
}
