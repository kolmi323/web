package s02;

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
                        "\nНомер счёта: %d" +
                        "\nНазвание счёта: %s" +
                        "\nБаланс на счету: %f\n",
                        rs.getInt("id"), rs.getString("name"), rs.getDouble("balance")
                        );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAccount() {
        String nameAccount = this.userDataReceiver.enterNameAccount();
        double balance = this.userDataReceiver.enterBalanceAccount();
        if (isDataNull(nameAccount) || isDataNull(balance)) {
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement("insert into account (user_id, name, balance) values(?, ?, ?)");
            ps.setInt(1, this.userId);
            ps.setString(2, nameAccount);
            ps.setDouble(3, balance);
            ps.execute();
            System.out.println("Account Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAccount() {
        try {
            InputRequest inputRequest = new InputRequest();
            String answer = inputRequest.requestStr("The account name to delete: ").toLowerCase();
            if (answer.isEmpty()) {
                System.out.println("Answer cannot be empty");
                return;
            }
            if (isAccountContains(answer)) {
                PreparedStatement ps = con.prepareStatement("delete from account where name = ? and user_id = ?");
                ps.setString(1, answer);
                ps.setInt(2, userId);
                ps.execute();
                System.out.println("Account Deleted");
            } else {
                System.out.println("Account not found");
            }
        } catch (SQLException e) {
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

    private boolean isDataNull(Object object) {
        if (object == null) {
            System.out.println("Data cannot be empty");
            return true;
        }
        return false;
    }
}
