package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.AccountDAO;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.AccountModel;
import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks AccountService subj;

    @Mock AccountDAO accountDAO;
    @Mock ConverterAccountModelToAccountDTO converter;

    @Test
    public void getAll_shouldReturnAllAccounts_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(1, 1, "bank", new BigDecimal("1000.50"));
        List<AccountModel> accountsModelsList = new ArrayList<>();
        accountsModelsList.add(accountModel);
        when(accountDAO.getAll(1)).thenReturn(accountsModelsList);

        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.50"));
        when(converter.convert(accountModel)).thenReturn(accountDTO);

        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        List<AccountDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(accountDAO, times(1)).getAll(1);
        verify(converter, times(1)).convert(accountModel);
    }

    @Test
    public void getAll_shouldReturnEmptyList_whenCalledWithValidArguments() {
        List<AccountModel> accountsModelsList = new ArrayList<>();
        when(accountDAO.getAll(1)).thenReturn(accountsModelsList);

        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<AccountDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(accountDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getAll_shouldAcceptDAOException_whenCalledWithValidArguments() {
        when(accountDAO.getAll(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1));

        verify(accountDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_shouldReturnAccountDTO_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(1, 1, "bank", new BigDecimal("1000.50"));
        when(accountDAO.insert(1, "bank", new BigDecimal("1000.50"))).thenReturn(accountModel);

        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.50"));
        when(converter.convert(accountModel)).thenReturn(accountDTO);

        AccountDTO account = subj.create(1, "bank", new BigDecimal("1000.50"));
        assertEquals(accountDTO, account);

        verify(accountDAO, times(1))
                .insert(1, "bank", new BigDecimal("1000.50"));
        verify(converter, times(1)).convert(accountModel);
    }

    @Test
    public void create_shouldAcceptDAOExceptionWithMessageAboutFailedCreated_whenCalledWithValidArguments() {
        when(accountDAO.insert(1, "bank", new BigDecimal("1000.50")))
                .thenThrow(new DAOException("Insert account failed"));

        DAOException exception = assertThrows(DAOException.class,
                () -> subj.create(1, "bank", new BigDecimal("1000.50")));
        assertEquals("Insert account failed", exception.getMessage());

        verify(accountDAO, times(1))
                .insert(1, "bank", new BigDecimal("1000.50"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_shouldAcceptAlreadyExistsException_whenCalledWithValidArguments() {
        when(accountDAO.insert(1, "bank", new BigDecimal("1000.50")))
                .thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> subj
                .create(1, "bank", new BigDecimal("1000.50")));

        verify(accountDAO, times(1))
                .insert(1, "bank", new BigDecimal("1000.50"));
        verifyNoInteractions(converter);
    }

    @Test
    public void create_shouldAcceptDAOException_whenCalledWithValidArguments() {
        when(accountDAO.insert(1, "bank", new BigDecimal("1000.50"))).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(1, "bank", new BigDecimal("1000.50")));

        verify(accountDAO, times(1))
                .insert(1, "bank", new BigDecimal("1000.50"));
        verifyNoInteractions(converter);
    }

    @Test
    public void delete_shouldReturnSuccess_whenCalledWithValidArguments() {
        when(accountDAO.delete(1, 1)).thenReturn(true);

        assertTrue(subj.delete(1, 1));

        verify(accountDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_shouldReturnFailed_whenCalledWithValidArguments() {
        when(accountDAO.delete(1, 1)).thenReturn(false);

        assertFalse(subj.delete(1, 1));

        verify(accountDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_shouldAcceptDAOException_whenCalledWithValidArguments() {
        when(accountDAO.delete(1, 1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.delete(1, 1));

        verify(accountDAO, times(1)).delete(1, 1);
    }
}