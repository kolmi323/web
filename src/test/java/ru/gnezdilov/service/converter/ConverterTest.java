package ru.gnezdilov.service.converter;

import org.junit.Test;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.dao.model.TypeModel;
import ru.gnezdilov.dao.model.UserModel;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ConverterTest {
    @Test
    public void convert_returnAccountDTO_whenCalledWithValidArguments() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        AccountModel accountModel = new AccountModel(1, 1, "Bank", new BigDecimal("1000.00"));
        AccountDTO accountDTO = new AccountDTO(1, 1, "Bank", new BigDecimal("1000.00"));
        ReturnDTO(converter, accountModel, accountDTO);
    }

    @Test
    public void convert_returnTypeDTO_whenCalledWithValidArguments() {
        ConverterTypeModelToTypeDTO converter = new ConverterTypeModelToTypeDTO();
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        ReturnDTO(converter, typeModel, typeDTO);
    }

    @Test
    public void convert_returnUserDTO_whenCalledWithValidArguments() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        UserModel userModel = new UserModel(1, "Anton", "anton@mail.ru", "hash");
        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        ReturnDTO(converter, userModel, userDTO);
    }

    @Test
    public void converter_returnTransactionDTO_whenCalledWithValidArguments() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        ReturnDTO(converter, transactionModel, transactionDTO);
    }

    @Test
    public void convert_accountThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        ThrowNullPointerException(converter);
    }

    @Test
    public void converter_typeThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterTypeModelToTypeDTO converter
                = new ConverterTypeModelToTypeDTO();
        ThrowNullPointerException(converter);
    }

    @Test
    public void converter_userThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        ThrowNullPointerException(converter);
    }

    @Test
    public void converter_transactionThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        ThrowNullPointerException(converter);
    }

    private <S, T> void ReturnDTO(Converter<S, T> converter, S model, T dto) {
        T result = converter.convert(model);
        assertEquals(dto, result);
    }

    private <S, T> void ThrowNullPointerException(Converter<S, T> converter) {
        assertNull(converter.convert(null));
    }
}