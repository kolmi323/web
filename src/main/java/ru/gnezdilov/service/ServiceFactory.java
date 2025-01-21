package ru.gnezdilov.service;

import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.dao.DaoFactory;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.service.personal.TypeService;

public final class ServiceFactory {
    private static ServiceFactory instance;

    private final DaoFactory daoFactory;

    private AuthService authService;
    private AccountService accountService;
    private TypeService typeService;
    private TransactionService transactionService;

    private ConverterAccountModelToAccountDTO converterToAccountDTO;
    private ConverterUserModelToUserDTO converterToUserDTO;
    private ConverterTypeModelToTypeDTO converterToTypeDTO;
    private ConverterTransactionModelToTransactionDTO converterToTransactionDTO;
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

    private ConverterTypeModelToTypeDTO getConverterToTypeDTO() {
        if (converterToTypeDTO == null) {
            converterToTypeDTO = new ConverterTypeModelToTypeDTO();
        }
        return converterToTypeDTO;
    }

    private ConverterTransactionModelToTransactionDTO getConverterToTransactionDTO() {
        if (converterToTransactionDTO == null) {
            converterToTransactionDTO = new ConverterTransactionModelToTransactionDTO();
        }
        return converterToTransactionDTO;
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

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = new TypeService(this.daoFactory.getTypeDao(),
                    getConverterToTypeDTO());
        }
        return typeService;
    }

    public TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(this.daoFactory.getTransactionDao(),
                    getConverterToTransactionDTO());
        }
        return transactionService;
    }
}
