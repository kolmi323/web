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
import java.sql.Date;

import static org.junit.Assert.*;

public class ConverterTest {
    private <S, T> void convert_ReturnDTO(Converter<S, T> converter, S model, T dto) {
        T result = converter.convert(model);
        assertEquals(dto, result);
    }

    private <S, T> void convert_ThrowNullPointerException(Converter<S, T> converter) {
        assertThrows(NullPointerException.class, () -> converter.convert(null));
    }

    @Test
    public void convert_ReturnAccountDTO() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        AccountModel accountModel = new AccountModel(1, 1, "Bank", new BigDecimal("1000.00"));
        AccountDTO accountDTO = new AccountDTO(1, 1, "Bank", new BigDecimal("1000.00"));
        convert_ReturnDTO(converter, accountModel, accountDTO);
    }

    @Test
    public void convert_ReturnTypeDTO() {
        ConverterTypeModelToTypeDTO converter
                = new ConverterTypeModelToTypeDTO();
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        convert_ReturnDTO(converter, typeModel, typeDTO);
    }

    @Test
    public void convert_ReturnUserDTO() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        UserModel userModel = new UserModel(1, "Anton", "anton@mail.ru", "hash");
        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        convert_ReturnDTO(converter, userModel, userDTO);
    }

    @Test
    public void converter_ReturnTransactionDTO() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.10"), Date.valueOf("2025-01-21"));
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.10"), Date.valueOf("2025-01-21"));
        convert_ReturnDTO(converter, transactionModel, transactionDTO);
    }

    @Test
    public void convert_AccountThrowNullPointerException() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        convert_ThrowNullPointerException(converter);
    }

    @Test
    public void converter_TypeThrowNullPointerException() {
        ConverterTypeModelToTypeDTO converter
                = new ConverterTypeModelToTypeDTO();
        convert_ThrowNullPointerException(converter);
    }

    @Test
    public void converter_UserThrowNullPointerException() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        convert_ThrowNullPointerException(converter);
    }

    @Test
    public void converter_TransactionThrowNullPointerException() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        convert_ThrowNullPointerException(converter);
    }
}