package ru.gnezdilov.view;

import ru.gnezdilov.view.auth.LoginPage;
import ru.gnezdilov.view.auth.LoginWindow;
import ru.gnezdilov.view.auth.RegisterWindow;
import ru.gnezdilov.view.personal.*;
import ru.gnezdilov.service.ServiceFactory;
import ru.gnezdilov.service.dto.UserDTO;
import web.view.personal.*;

public final class ViewFactory {
    private static ViewFactory instance;

    private static UserDTO currentUser;

    private final ServiceFactory serviceFactory;

    private LoginPage loginPage;
    private RegisterWindow registerWindow;
    private LoginWindow loginWindow;
    private PersonalOfficePage personalOfficePage;
    private AccountMenu accountMenu;
    private AccountActionMenu accountActionMenu;
    private TypeTransactionMenu typeTransactionMenu;
    private TypeTransactionActionMenu typeTransactionActionMenu;

    private UIUtils utils;
    private InputRequest inputRequest;

    private ViewFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    private UIUtils getUtils() {
        if (utils == null) {
            utils = new UIUtils();
        }
        return utils;
    }

    private InputRequest getInputRequest() {
        if (inputRequest == null) {
            inputRequest = new InputRequest();
        }
        return inputRequest;
    }

    public static ViewFactory getInstance() {
        if (instance == null) {
            instance = new ViewFactory(ServiceFactory.getInstance());
        }
        return instance;
    }

    public static void setCurrentUser(UserDTO userDTO) {
        currentUser = userDTO;
    }

    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(getInputRequest(), getUtils(), getRegisterWindow(), getLoginWindow());
        }
        return loginPage;
    }

    public RegisterWindow getRegisterWindow() {
        if (registerWindow == null) {
            registerWindow = new RegisterWindow(getUtils(), getPersonalOfficePage(), serviceFactory.getRegistration());
        }
        return registerWindow;
    }

    public LoginWindow getLoginWindow() {
        if (loginWindow == null) {
            loginWindow = new LoginWindow(getUtils(), serviceFactory.getAuthorization(), getPersonalOfficePage());
        }
        return loginWindow;
    }

    public PersonalOfficePage getPersonalOfficePage() {
        if (personalOfficePage == null) {
            personalOfficePage = new PersonalOfficePage(getInputRequest(), getUtils(), getAccountMenu(), getTypeTransactionMenu());
        }
        return personalOfficePage;
    }

    public AccountMenu getAccountMenu() {
        if (accountMenu == null) {
            accountMenu = new AccountMenu(getUtils(), getInputRequest(), getAccountActionMenu());
        }
        return accountMenu;
    }

    public AccountActionMenu getAccountActionMenu() {
        if (accountActionMenu == null) {
            accountActionMenu = new AccountActionMenu(getUtils(), serviceFactory.getAccountService());
        }
        return accountActionMenu;
    }

    public TypeTransactionMenu getTypeTransactionMenu() {
        if (typeTransactionMenu == null) {
            typeTransactionMenu = new TypeTransactionMenu(getInputRequest(), getUtils(), getTypeTransactionActionMenu());
        }
        return typeTransactionMenu;
    }

    public TypeTransactionActionMenu getTypeTransactionActionMenu() {
        if (typeTransactionActionMenu == null) {
            typeTransactionActionMenu = new TypeTransactionActionMenu(getUtils(), serviceFactory.getTypeTransactionService());
        }
        return typeTransactionActionMenu;
    }
}
