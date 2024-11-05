package s02;

import java.math.BigDecimal;
import java.sql.*;

public class ActionControlPanel {
    private Connection con;
    private UserDataReceiver userDataReceiver;
    private int userId;

    public ActionControlPanel(Connection con, int userId) {
        this.con = con;
        this.userId = userId;
        this.userDataReceiver = new UserDataReceiver(con);
    }

    public void showListAccounts() {
        try {
            ResultSet rs = getListAccount();
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
        try {
            String nameAccount = this.userDataReceiver.enterNameAccount(userId);
            BigDecimal balance = this.userDataReceiver.enterBalanceAccount();
            PreparedStatement ps = con.prepareStatement("insert into account (user_id, name, balance) values(?, ?, ?)");
            ps.setInt(1, this.userId);
            ps.setString(2, nameAccount);
            ps.setBigDecimal(3, balance);
            ps.execute();
            System.out.println("Account Added");
        } catch (ActionUserExitException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount() {
        try {
            UserDataReceiver userDataReceiver = new UserDataReceiver(con);
            String answer = userDataReceiver.enterName();
            if (isAccountContains(answer)) {
                PreparedStatement ps = con.prepareStatement("delete from account where name = ? and user_id = ?");
                ps.setString(1, answer);
                ps.setInt(2, userId);
                ps.execute();
                System.out.println("Account Deleted");
            } else {
                System.out.println("Account not found");
            }
        } catch (ActionUserExitException e) {
            System.out.println(e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private ResultSet getListAccount() throws SQLException {
        Statement st = this.con.createStatement();
        return st.executeQuery("select * from account where user_id = " + userId);
    }

    private boolean isAccountContains(String name) throws SQLException {
        ResultSet rs = getListAccount();
        while (rs.next()) {
            if (rs.getString("name").equals(name) && rs.getInt("user_id") == this.userId) {
                return true;
            }
        }
        return false;
    }
}
