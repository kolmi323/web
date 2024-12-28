package web.view;

import web.service.DTO.UserDTO;
import web.view.PersonalOffice.*;
import web.view.StartPage.LoginPage;
import web.view.StartPage.LoginWindow;
import web.view.StartPage.RegisterWindow;

public class ViewFactory {
    private static UserDTO currentUser;
    private static LoginPage loginPage;
    private static RegisterWindow registerWindow;
    private static LoginWindow loginWindow;
    private static PersonalOfficePage personalOfficePage;
    private static AccountMenu accountMenu;
    private static AccountActionMenu accountActionMenu;
    private static TypeTransactionMenu typeTransactionMenu;
    private static TypeTransactionActionMenu typeTransactionActionMenu;

    public static void setCurrentUser(UserDTO userDTO) {
        currentUser = userDTO;
    }

    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    public static LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public static RegisterWindow getRegisterWindow() {
        if (registerWindow == null) {
            registerWindow = new RegisterWindow();
        }
        return registerWindow;
    }

    public static LoginWindow getLoginWindow() {
        if (loginWindow == null) {
            loginWindow = new LoginWindow();
        }
        return loginWindow;
    }

    public static PersonalOfficePage getPersonalOfficePage() {
        if (personalOfficePage == null) {
            personalOfficePage = new PersonalOfficePage();
        }
        return personalOfficePage;
    }

    public static AccountMenu getAccountMenu() {
        if (accountMenu == null) {
            accountMenu = new AccountMenu();
        }
        return accountMenu;
    }

    public static AccountActionMenu getAccountActionMenu() {
        if (accountActionMenu == null) {
            accountActionMenu = new AccountActionMenu();
        }
        return accountActionMenu;
    }

    public static TypeTransactionMenu getTypeTransactionMenu() {
        if (typeTransactionMenu == null) {
            typeTransactionMenu = new TypeTransactionMenu();
        }
        return typeTransactionMenu;
    }

    public static TypeTransactionActionMenu getTypeTransactionActionMenu() {
        if (typeTransactionActionMenu == null) {
            typeTransactionActionMenu = new TypeTransactionActionMenu();
        }
        return typeTransactionActionMenu;
    }
}
