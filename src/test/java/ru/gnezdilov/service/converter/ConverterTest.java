package ru.gnezdilov.service.converter;

import org.junit.Test;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.entities.TransactionModel;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConverterTest {
    @Test
    public void convert_returnAccountDTO_whenCalledWithValidArguments() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        AccountModel accountModel = new AccountModel(1, 1, "Bank", new BigDecimal("1000.00"));
        AccountDTO accountDTO = new AccountDTO(1, 1, "Bank", new BigDecimal("1000.00"));
        returnDTO(converter, accountModel, accountDTO);
    }

    @Test
    public void convert_returnTypeDTO_whenCalledWithValidArguments() {
        ConverterTypeModelToTypeDTO converter = new ConverterTypeModelToTypeDTO();
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        returnDTO(converter, typeModel, typeDTO);
    }

    @Test
    public void convert_returnUserDTO_whenCalledWithValidArguments() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setName("Anton");
        userModel.setEmail("anton@mail.ru");
        userModel.setPassword("hash");
        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        returnDTO(converter, userModel, userDTO);
    }

    @Test
    public void converter_returnTransactionDTO_whenCalledWithValidArguments() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        returnDTO(converter, transactionModel, transactionDTO);
    }

    @Test (expected = NullPointerException.class)
    public void convert_accountThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        throwNullPointerException(converter);
    }

    @Test (expected = NullPointerException.class)
    public void converter_typeThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterTypeModelToTypeDTO converter
                = new ConverterTypeModelToTypeDTO();
        throwNullPointerException(converter);
    }

    @Test (expected = NullPointerException.class)
    public void converter_userThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterUserModelToUserDTO converter = new ConverterUserModelToUserDTO();
        throwNullPointerException(converter);
    }

    @Test (expected = NullPointerException.class)
    public void converter_transactionThrowNullPointerException_whenCalledWithValidArguments() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        throwNullPointerException(converter);
    }

    private <S, T> void returnDTO(Converter<S, T> converter, S model, T dto) {
        T result = converter.convert(model);
        assertEquals(dto, result);
    }

    private <S, T> void throwNullPointerException(Converter<S, T> converter) {
        assertNull(converter.convert(null));
    }
}