package s02;

import s02.pages.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        LoginPage loginPage;
        try (Connection con = DriverManager.getConnection(
             "jdbc:postgresql://localhost:5432/postgres",
             "postgres",
             "postgres")) {
            loginPage = new LoginPage(con);
            loginPage.startPage();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
