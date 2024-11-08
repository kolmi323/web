package s02;

import java.math.BigDecimal;
import java.sql.*;

public class ActionControlPanel {
    private Connection con;
    private UserDataReceiver userDataReceiver;
    private int userId;
    private ControlExit controlExit;

    public ActionControlPanel(Connection con, int userId) {
        this.con = con;
        this.userId = userId;
        this.userDataReceiver = new UserDataReceiver(con);
        this.controlExit = new ControlExit();
    }

    public void showListAccounts() {
        try (ResultSet rs = getListAccount()) {
            while (rs.next()) {
                System.out.printf(
                        "\nНазвание счёта: %s" +
                        "\nБаланс на счету: %f\n",
                        rs.getString("name"), rs.getDouble("balance")
                        );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAccount() {
        try (PreparedStatement ps = con.prepareStatement(
                "insert into account (user_id, name, balance) values(?, ?, ?)"
        )) {
            String nameAccount = this.userDataReceiver.enterNameAccount(userId);
            if (controlExit.isExit(nameAccount)) return;
            BigDecimal balance = this.userDataReceiver.enterBalanceAccount();
            if (controlExit.isExit(balance)) return;
            ps.setInt(1, this.userId);
            ps.setString(2, nameAccount);
            ps.setBigDecimal(3, balance);
            ps.execute();
            System.out.println("Account Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount() {
        try (PreparedStatement ps = con.prepareStatement(
                "delete from account where name = ? and user_id = ?"
        )) {
            UserDataReceiver userDataReceiver = new UserDataReceiver(con);
            String answer = userDataReceiver.enterName();
            if (controlExit.isExit(answer)) return;
            ps.setString(1, answer);
            ps.setInt(2, userId);
            if (ps.executeUpdate() == 0) {
                System.out.println("Nothing deleted");
            } else {
                System.out.println("Deleted successfully");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private ResultSet getListAccount() throws SQLException {
        Statement st = this.con.createStatement();
        return st.executeQuery("select * from account where user_id = " + userId);
    }
}
