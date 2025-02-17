package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TransactionDAO;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.ServiceException;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.converter.ConverterTransactionModelToTransactionDTO;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.dto.CategoryTransactionDTO;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks private TransactionService subj;

    @Mock private TransactionDAO transactionDAO;
    @Mock private AccountService accountService;
    @Mock private CategoryTransactionService categoryTransactionService;
    @Mock private TypeService typeService;
    @Mock private ConverterTransactionModelToTransactionDTO converter;
}

