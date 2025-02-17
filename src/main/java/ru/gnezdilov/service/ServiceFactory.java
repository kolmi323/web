package ru.gnezdilov.service;

import ru.gnezdilov.service.converter.*;
import ru.gnezdilov.dao.DaoFactory;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.service.personal.CategoryTransactionService;
import ru.gnezdilov.service.personal.TransactionService;
import ru.gnezdilov.service.personal.TypeService;

public final class ServiceFactory {
    private static ServiceFactory instance;

    private final DaoFactory daoFactory;

    private AuthService authService;
    private AccountService accountService;
    private TypeService typeService;
    private CategoryTransactionService categoryTransactionService;
    private TransactionService transactionService;

    private ConverterAccountModelToAccountDTO converterToAccountDTO;
    private ConverterUserModelToUserDTO converterToUserDTO;
    private ConverterTypeModelToTypeDTO converterToTypeDTO;
    private ConverterTransactionModelToTransactionDTO converterToTransactionDTO;
    private ConverterCategoryTransactionModelToCategoryTransactionDTO converterToCategoryTransactionDTO;
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

    public CategoryTransactionService getCategoryTransactionService() {
        if (categoryTransactionService == null) {
            categoryTransactionService = new CategoryTransactionService(this.daoFactory.getCategoryTransactionDao(),
                    getConverterToCategoryTransactionDTO());
        }
        return categoryTransactionService;
    }

    public TransactionService getTransactionService() {
        if (transactionService == null) {
            transactionService = new TransactionService(this.daoFactory.getTransactionDao(),
                    getAccountService(), getTypeService(), getConverterToTransactionDTO());
        }
        return transactionService;
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

    private ConverterCategoryTransactionModelToCategoryTransactionDTO getConverterToCategoryTransactionDTO() {
        if (converterToCategoryTransactionDTO == null) {
            converterToCategoryTransactionDTO = new ConverterCategoryTransactionModelToCategoryTransactionDTO();
        }
        return converterToCategoryTransactionDTO;
    }
}
