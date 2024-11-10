package s02.pages;

import s02.ActionControlPanel;
import s02.CustomException.BadCredentialsException;
import s02.ControlExit;
import s02.InputRequest;

import java.sql.*;

public class PersonalOfficePage {
    private Connection con;
    private int userId;
    private InputRequest inputRequest;
    ControlExit controlExit;

    public PersonalOfficePage(Connection con, int userId) {
        this.con = con;
        this.userId = userId;
        this.inputRequest = new InputRequest();
        this.controlExit = new ControlExit();
    }

    public void startPersonalOffice() throws BadCredentialsException {
        String answer;
        try (PreparedStatement ps = con.prepareStatement("select * from users where id = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Welcome to personal office " + rs.getString("name") + "!");
                while (true) {
                    answer = this.inputRequest.requestStr(
                            "Action menu:" +
                                    "\n1. Display a list of accounts" +
                                    "\n2. Create account" +
                                    "\n3. Delete account" +
                                    "\nexit. Exit"
                    );
                    ActionControlPanel actionControlPanel = new ActionControlPanel(this.con, this.userId);
                    if (answer.equals("1")) {
                        actionControlPanel.showListAccounts();
                    } else if (answer.equals("2")) {
                        actionControlPanel.addAccount();
                    } else if (answer.equals("3")) {
                        actionControlPanel.deleteAccount();
                    } else if (controlExit.isExit(answer)) {
                        System.out.println("You exit from person office!");
                        return;
                    } else {
                        System.out.println("Invalid input");
                    }
                }
            } else {
                throw new BadCredentialsException();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
