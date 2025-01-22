package ru.gnezdilov.view;

import ru.gnezdilov.view.auth.LoginPage;
import ru.gnezdilov.view.auth.LoginWindow;
import ru.gnezdilov.view.auth.RegisterWindow;
import ru.gnezdilov.view.personal.*;
import ru.gnezdilov.service.ServiceFactory;
import ru.gnezdilov.service.dto.UserDTO;

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
    private TypeMenu typeMenu;
    private TypeActionMenu typeActionMenu;
    private CategoryTransactionActionMenu categoryTransactionActionMenu;
    private CategoryTransactionMenu categoryTransactionMenu;

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
            registerWindow = new RegisterWindow(getUtils(), getPersonalOfficePage(), serviceFactory.getAuthService());
        }
        return registerWindow;
    }

    public LoginWindow getLoginWindow() {
        if (loginWindow == null) {
            loginWindow = new LoginWindow(getUtils(), serviceFactory.getAuthService(), getPersonalOfficePage());
        }
        return loginWindow;
    }

    public PersonalOfficePage getPersonalOfficePage() {
        if (personalOfficePage == null) {
            personalOfficePage = new PersonalOfficePage(getInputRequest(), getUtils(), getAccountMenu(),
                    getTypeMenu(), getTransactionMenu());
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

    public TypeMenu getTypeMenu() {
        if (typeMenu == null) {
            typeMenu = new TypeMenu(getInputRequest(), getUtils(), getTypeActionMenu());
        }
        return typeMenu;
    }

    public TypeActionMenu getTypeActionMenu() {
        if (typeActionMenu == null) {
            typeActionMenu = new TypeActionMenu(getUtils(), serviceFactory.getTypeService());
        }
        return typeActionMenu;
    }

    public CategoryTransactionActionMenu getTransactionActionMenu() {
        if (categoryTransactionActionMenu == null) {
            categoryTransactionActionMenu = new CategoryTransactionActionMenu(getUtils(), this.serviceFactory.getTransactionService(),
                    getTypeActionMenu(), getAccountActionMenu());
        }
        return categoryTransactionActionMenu;
    }

    public CategoryTransactionMenu getTransactionMenu() {
        if (categoryTransactionMenu == null) {
            categoryTransactionMenu = new CategoryTransactionMenu(getInputRequest(), getUtils(), getTransactionActionMenu());
        }
        return categoryTransactionMenu;
    }
}
