package ru.gnezdilov.service;

import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.converter.ConverterTypeTransactionModelToTypeTransactionDTO;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.dao.DaoFactory;
import ru.gnezdilov.service.auth.Authorization;
import ru.gnezdilov.service.auth.Registration;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.TypeTransactionService;

public final class ServiceFactory {
    private static ServiceFactory instance;

    private final DaoFactory daoFactory;

    private Authorization authorization;
    private Registration registration;
    private AccountService accountService;
    private TypeTransactionService typeTransactionService;

    private ConverterAccountModelToAccountDTO converterAccountModelToAccountDTO;
    private ConverterUserModelToUserDTO converterUserModelToUserDTO;
    private ConverterTypeTransactionModelToTypeTransactionDTO converterTypeTransactionModelToTypeTransactionDTO;
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

    private ConverterUserModelToUserDTO getConverterUserModelToUserDTO() {
        if (converterUserModelToUserDTO == null) {
            converterUserModelToUserDTO = new ConverterUserModelToUserDTO();
        }
        return converterUserModelToUserDTO;
    }

    private ConverterAccountModelToAccountDTO getConverterAccountModelToAccountDTO() {
        if (converterAccountModelToAccountDTO == null) {
            converterAccountModelToAccountDTO = new ConverterAccountModelToAccountDTO();
        }
        return converterAccountModelToAccountDTO;
    }

    private ConverterTypeTransactionModelToTypeTransactionDTO getConverterTypeTransactionModelToTypeTransactionDTO() {
        if (converterTypeTransactionModelToTypeTransactionDTO == null) {
            converterTypeTransactionModelToTypeTransactionDTO = new ConverterTypeTransactionModelToTypeTransactionDTO();
        }
        return converterTypeTransactionModelToTypeTransactionDTO;
    }

    public Authorization getAuthorization() {
        if (authorization == null) {
            authorization = new Authorization(this.daoFactory.getUserDAO(), getDigestService(),
                    getConverterUserModelToUserDTO());
        }
        return authorization;
    }

    public Registration getRegistration() {
        if (registration == null) {
            registration = new Registration(this.daoFactory.getUserDAO(), getDigestService(),
                    getConverterUserModelToUserDTO());
        }
        return registration;
    }

    public AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(this.daoFactory.getAccountDao(),
                    getConverterAccountModelToAccountDTO());
        }
        return accountService;
    }

    public TypeTransactionService getTypeTransactionService() {
        if (typeTransactionService == null) {
            typeTransactionService = new TypeTransactionService(this.daoFactory.getTypeTransactionDao(),
                    getConverterTypeTransactionModelToTypeTransactionDTO());
        }
        return typeTransactionService;
    }
}
