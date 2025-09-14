package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.AccountRepository;
import ru.gnezdilov.dao.entities.AccountModel;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.service.converter.ConverterAccountModelToAccountDTO;
import ru.gnezdilov.service.dto.AccountDTO;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks private AccountService subj;

    @Mock private AccountRepository accountRepository;
    @Mock private ConverterAccountModelToAccountDTO converter;

    @Test
    public void getAll_shouldReturnAllAccounts_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(1, 1, "bank", new BigDecimal("1000.50"));
        List<AccountModel> accountsModelsList = new ArrayList<>();
        accountsModelsList.add(accountModel);
        when(accountRepository.findAllByUserId(1)).thenReturn(accountsModelsList);

        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.50"));
        when(converter.convert(accountModel)).thenReturn(accountDTO);

        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(accountDTO);
        List<AccountDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(accountRepository, times(1)).findAllByUserId(1);
        verify(converter, times(1)).convert(accountModel);
    }

    @Test
    public void getAll_shouldReturnEmptyList_whenCalledWithValidArguments() {
        List<AccountModel> accountsModelsList = new ArrayList<>();
        when(accountRepository.findAllByUserId(1)).thenReturn(accountsModelsList);

        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<AccountDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(accountRepository, times(1)).findAllByUserId(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_returnAccountDTO_whenCalledWithValidArguments() {
        AccountModel accountRequest = new AccountModel();
        accountRequest.setUserId(1);
        accountRequest.setName("bank");
        accountRequest.setBalance(new BigDecimal("1000.50"));
        AccountModel accountModel = new AccountModel(1, 1, "bank", new BigDecimal("1000.50"));
        when(accountRepository.save(accountRequest)).thenReturn(accountModel);

        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.50"));
        when(converter.convert(accountModel)).thenReturn(accountDTO);

        AccountDTO account = subj.create(1, "bank", new BigDecimal("1000.50"));
        assertEquals(accountDTO, account);

        verify(accountRepository, times(1)).save(accountRequest);
        verify(converter, times(1)).convert(accountModel);
    }

    @Test
    public void create_throwIllegalArgumentException_whenCalledWithInvalidArguments() {
        AccountModel accountRequest = new AccountModel();
        accountRequest.setUserId(1);
        when(accountRepository.save(accountRequest))
                .thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.create(1, null, null));

        verify(accountRepository, times(1)).save(accountRequest);
        verifyNoInteractions(converter);
    }

    @Test
    public void delete_shouldReturnSuccess_whenCalledWithValidArguments() {
        when(accountRepository.deleteByIdAndUserId(1, 1)).thenReturn(1);

        assertTrue(subj.delete(1, 1));

        verify(accountRepository, times(1)).deleteByIdAndUserId(1, 1);
    }

    @Test
    public void delete_shouldReturnFailed_whenCalledWithValidArguments() {
        when(accountRepository.deleteByIdAndUserId(1, 1)).thenReturn(0);

        assertFalse(subj.delete(1, 1));

        verify(accountRepository, times(1)).deleteByIdAndUserId(1, 1);
    }

    @Test
    public void existsByIdAndUserId_returnTrue_whenCalledWithValidArguments() {
        when(accountRepository.existsByIdAndUserId(1, 1)).thenReturn(true);

        assertTrue(subj.existsByIdAndUserId(1, 1));

        verify(accountRepository, times(1)).existsByIdAndUserId(1, 1);
    }

    @Test
    public void existsByIdAndUserId_returnFalse_whenCalledWithInvalidArguments() {
        when(accountRepository.existsByIdAndUserId(1, 2)).thenReturn(false);

        assertFalse(subj.existsByIdAndUserId(1, 2));
        verify(accountRepository, times(1)).existsByIdAndUserId(1, 2);
    }

    @Test
    public void getByIdAndUserId_returnAccountDTO_whenCalledWithValidArguments() {
        AccountModel accountModel = new AccountModel(1, 1, "bank", new BigDecimal("1000.00"));
        when(accountRepository.findByIdAndUserId(1, 1)).thenReturn(accountModel);

        AccountDTO accountDTO = new AccountDTO(1, 1, "bank", new BigDecimal("1000.00"));
        when(converter.convert(accountModel)).thenReturn(accountDTO);

        assertEquals(accountDTO, subj.getByIdAndUserId(1, 1));
        verify(accountRepository, times(1)).findByIdAndUserId(1, 1);
        verify(converter, times(1)).convert(accountModel);
    }

    @Test
    public void getByIdAndUserId_acceptEntityNotFoundException_whenCalledWithInvalidArguments() {
        when(accountRepository.findByIdAndUserId(1, 2)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> subj.getByIdAndUserId(1, 2));
        verify(accountRepository, times(1)).findByIdAndUserId(1, 2);
        verifyNoInteractions(converter);
    }
}