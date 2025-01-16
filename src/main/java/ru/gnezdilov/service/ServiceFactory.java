package ru.gnezdilov.service;

import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.converter.ConverterTypeTransactionModelToTypeTransactionDTO;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.dao.DaoFactory;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.TypeTransactionService;

public final class ServiceFactory {
    private static ServiceFactory instance;

    private final DaoFactory daoFactory;

    private AuthService authService;
    private AccountService accountService;
    private TypeTransactionService typeTransactionService;

    private ConverterAccountModelToAccountDTO converterToAccountDTO;
    private ConverterUserModelToUserDTO converterToUserDTO;
    private ConverterTypeTransactionModelToTypeTransactionDTO converterToTypeTransactionDTO;
    private DigestService digestService;

    private ServiceFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory(DaoFactory.getInstance());
        }
        return instance;
    }

    private DigestService getDigestService() {
        if (digestService == null) {
            digestService = new MD5DigestUtils();
        }
        return digestService;
    }

    private ConverterUserModelToUserDTO getConverterToUserDTO() {
        if (converterToUserDTO == null) {
            converterToUserDTO = new ConverterUserModelToUserDTO();
        }
        return converterToUserDTO;
    }

    private ConverterAccountModelToAccountDTO getConverterToAccountDTO() {
        if (converterToAccountDTO == null) {
            converterToAccountDTO = new ConverterAccountModelToAccountDTO();
        }
        return converterToAccountDTO;
    }

    private ConverterTypeTransactionModelToTypeTransactionDTO getConverterToTypeTransactionDTO() {
        if (converterToTypeTransactionDTO == null) {
            converterToTypeTransactionDTO = new ConverterTypeTransactionModelToTypeTransactionDTO();
        }
        return converterToTypeTransactionDTO;
    }

    public AuthService getAuthService() {
        if (authService == null) {
            authService = new AuthService(this.daoFactory.getUserDAO(), getDigestService(), getConverterToUserDTO());
        }
        return authService;
    }

    public AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(this.daoFactory.getAccountDao(),
                    getConverterToAccountDTO());
        }
        return accountService;
    }

    public TypeTransactionService getTypeTransactionService() {
        if (typeTransactionService == null) {
            typeTransactionService = new TypeTransactionService(this.daoFactory.getTypeTransactionDao(),
                    getConverterToTypeTransactionDTO());
        }
        return typeTransactionService;
    }
}
