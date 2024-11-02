package s02.pages;

import s02.ActionControlPanel;
import s02.InputRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonalOfficePage {
    private Connection con;
    private int userId;
    private InputRequest inputRequest = new InputRequest();

    public PersonalOfficePage(Connection con, int userId) {
        this.con = con;
        this.userId = userId;
    }

    public void startPersonalOffice() {
        String answer;
        try {
            Statement st = this.con.createStatement();
            ResultSet rs = st.executeQuery("select * from users where id = " + this.userId);
            rs.next();
            System.out.println("Welcome to personal office " + rs.getString("name") + "!");
            while (true) {
                answer = this.inputRequest.requestStr(
                        "Action menu:" +
                        "\n1. Display a list of accounts" +
                        "\n2. Create account" +
                        "\n3. Delete account" +
                        "\n0. Exit"
                );
                ActionControlPanel actionControlPanel = new ActionControlPanel(this.con, this.userId);
                if (answer.equals("1")) {
                    actionControlPanel.showListAccounts();
                } else if (answer.equals("2")) {
                    actionControlPanel.addAccount();
                } else if (answer.equals("3")) {
                    actionControlPanel.deleteAccount();
                } else if (answer.equals("0")) {
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
