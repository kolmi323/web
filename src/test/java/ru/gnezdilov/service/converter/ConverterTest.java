package ru.gnezdilov.service.converter;

import org.junit.jupiter.api.Test;
import ru.gnezdilov.api.converter.*;
import ru.gnezdilov.api.json.account.create.AccountAddResponse;
import ru.gnezdilov.api.json.auth.AuthResponse;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.api.json.transaction.add.TransactionAddResponse;
import ru.gnezdilov.api.json.type.TypeResponse;
import ru.gnezdilov.dao.AccountRepository;
import ru.gnezdilov.dao.entities.*;
import ru.gnezdilov.service.dto.*;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConverterTest {
    @Test
    public void convert_returnAccountDTO_whenCalledWithValidArguments() {
        ConverterAccountModelToAccountDTO converter = new ConverterAccountModelToAccountDTO();
        AccountModel accountModel = new AccountModel(1, 1, "Bank", new BigDecimal("1000.00"));
        AccountDTO accountDTO = new AccountDTO(1, 1, "Bank", new BigDecimal("1000.00"));
        assertConverterResult(converter, accountModel, accountDTO);
    }

    @Test
    public void convert_returnTypeDTO_whenCalledWithValidArguments() {
        ConverterTypeModelToTypeDTO converter = new ConverterTypeModelToTypeDTO();
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        assertConverterResult(converter, typeModel, typeDTO);
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
        assertConverterResult(converter, userModel, userDTO);
    }

    @Test
    public void converter_returnTransactionDTO_whenCalledWithValidArguments() {
        ConverterTransactionModelToTransactionDTO converter = new ConverterTransactionModelToTransactionDTO();
        TransactionModel transactionModel = new TransactionModel(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2,
                new BigDecimal("1000.10"), LocalDate.parse("2025-01-21"));
        assertConverterResult(converter, transactionModel, transactionDTO);
    }

    @Test
    public void converter_returnAccountAddResponse_whenCalledWithValidArguments() {
        ConverterAccountDTOToAccountAddResponse converter = new ConverterAccountDTOToAccountAddResponse();
        AccountDTO accountDTO = new AccountDTO(1, 1, "sber", new BigDecimal("1000.00"));
        AccountAddResponse accountAddResponse = new AccountAddResponse(1, "sber", new BigDecimal("1000.00"));
        assertConverterResult(converter, accountDTO, accountAddResponse);
    }

    @Test
    public void converter_returnTransactionAddResponse_whenCalledWithValidArguments() {
        ConverterTransactionDTOToTransactionAddResponse converter = new ConverterTransactionDTOToTransactionAddResponse();
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 2, new BigDecimal("1000.00"), LocalDate.now());
        TransactionAddResponse transactionAddResponse = new TransactionAddResponse(1, new BigDecimal("1000.00"));
        assertConverterResult(converter, transactionDTO, transactionAddResponse);
    }

    @Test
    public void converter_returnTypeResponse_whenCalledWithValidArguments() {
        ConverterTypeDTOToTypeResponse converter = new ConverterTypeDTOToTypeResponse();
        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        TypeResponse typeResponse = new TypeResponse(1, "hobby");
        assertConverterResult(converter, typeDTO, typeResponse);
    }

    @Test
    public void converter_returnTAuthResponse_whenCalledWithValidArguments() {
        ConverterUserDTOToAuthResponse converter = new ConverterUserDTOToAuthResponse();
        UserDTO userDTO = new UserDTO(1, "john", "john@mail.ru");
        AuthResponse authResponse = new AuthResponse(1, "john@mail.ru");
        assertConverterResult(converter, userDTO, authResponse);
    }

    @Test
    public void converter_returnRegisterResponse_whenCalledWithValidArguments() {
        ConverterUserDTOToRegisterResponse converter = new ConverterUserDTOToRegisterResponse();
        UserDTO userDTO = new UserDTO(1, "john", "john@mail.ru");
        RegisterResponse registerResponse = new RegisterResponse(1, "john@mail.ru");
        assertConverterResult(converter, userDTO, registerResponse);
    }

    private <S, T> void assertConverterResult(Converter<S, T> converter, S extend, T actual) {
        T result = converter.convert(extend);
        assertEquals(actual, result);
    }
}