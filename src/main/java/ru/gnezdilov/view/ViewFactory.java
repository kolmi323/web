package ru.gnezdilov.view;

import ru.gnezdilov.view.auth.LoginPage;
import ru.gnezdilov.view.auth.LoginWindow;
import ru.gnezdilov.view.auth.RegisterWindow;
import ru.gnezdilov.view.personal.*;
import ru.gnezdilov.service.ServiceFactory;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.view.personal.Account.AccountActionMenu;
import ru.gnezdilov.view.personal.Account.AccountMenu;
import ru.gnezdilov.view.personal.CategoryTransaction.CategoryTransactionActionMenu;
import ru.gnezdilov.view.personal.CategoryTransaction.CategoryTransactionMenu;
import ru.gnezdilov.view.personal.Transaction.TransactionActionMenu;
import ru.gnezdilov.view.personal.Transaction.TransactionMenu;
import ru.gnezdilov.view.personal.Type.TypeActionMenu;
import ru.gnezdilov.view.personal.Type.TypeMenu;

public final class ViewFactory {
    private static ViewFactory instance;

    private static UserDTO currentUser;

    private final ServiceFactory serviceFactory;

    private LoginPage loginPage;
    private RegisterWindow registerWindow;
    private LoginWindow loginWindow;
    private PersonalOfficePage personalOfficePage;

    private AccountActionMenu accountActionMenu;
    private AccountMenu accountMenu;

    private TypeActionMenu typeActionMenu;
    private TypeMenu typeMenu;

    private CategoryTransactionActionMenu categoryTransactionActionMenu;
    private CategoryTransactionMenu categoryTransactionMenu;

    private TransactionActionMenu transactionActionMenu;
    private TransactionMenu transactionMenu;

    private UIUtils utils;
    private InputRequest inputRequest;

    private ViewFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
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
                    getTypeMenu(), getCategoryTransactionMenu(), getTransactionMenu());
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

    public CategoryTransactionActionMenu getCategoryTransactionActionMenu() {
        if (categoryTransactionActionMenu == null) {
            categoryTransactionActionMenu = new CategoryTransactionActionMenu(getUtils(), this.serviceFactory.getCategoryTransactionService(),
                    getTypeActionMenu(), getAccountActionMenu());
        }
        return categoryTransactionActionMenu;
    }

    public CategoryTransactionMenu getCategoryTransactionMenu() {
        if (categoryTransactionMenu == null) {
            categoryTransactionMenu = new CategoryTransactionMenu(getInputRequest(), getUtils(), getCategoryTransactionActionMenu());
        }
        return categoryTransactionMenu;
    }

    public TransactionActionMenu getTransactionActionMenu() {
        if (transactionActionMenu == null) {
            transactionActionMenu = new TransactionActionMenu(getUtils(), serviceFactory.getTransactionService(),
                    getAccountActionMenu(), getTypeActionMenu());
        }
        return transactionActionMenu;
    }

    public TransactionMenu getTransactionMenu() {
        if (transactionMenu == null) {
            transactionMenu = new TransactionMenu(getInputRequest(), getUtils(), getTransactionActionMenu());
        }
        return transactionMenu;
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
}
