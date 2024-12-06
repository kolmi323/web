package s03.view.StartPage;

import s03.service.ServiceDTO;

public class Main {
    public static void main(String[] args) {
        ServiceDTO service = new ServiceDTO();
        LoginPage loginPage = new LoginPage(service);
        System.out.println("Welcome to the Login Page");
        loginPage.startPage();
        System.out.println(("You exit from site. Good luck!"));
    }
}
