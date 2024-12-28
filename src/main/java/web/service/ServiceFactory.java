package web.service;

import web.dao.DaoFactory;
import web.service.PersonalOffice.AccountAction;
import web.service.PersonalOffice.TypeTransactionAction;
import web.service.StartPage.Authorization;
import web.service.StartPage.Registration;

public class ServiceFactory {
    private static Authorization authorization;
    private static Registration registration;
    private static AccountAction accountAction;
    private static TypeTransactionAction typeTransactionAction;

    public static Authorization getAuthorization() {
        if (authorization == null) {
            authorization = new Authorization(DaoFactory.getUserDAO());
        }
        return authorization;
    }

    public static Registration getRegistration() {
        if (registration == null) {
            registration = new Registration(DaoFactory.getUserDAO());
        }
        return registration;
    }

    public static AccountAction getAccountAction() {
        if (accountAction == null) {
            accountAction = new AccountAction();
        }
        return accountAction;
    }

    public static TypeTransactionAction getTypeTransactionAction() {
        if (typeTransactionAction == null) {
            typeTransactionAction = new TypeTransactionAction();
        }
        return typeTransactionAction;
    }
}
