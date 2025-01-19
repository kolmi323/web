package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TypeTransactionDAO;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TypeTransactionModel;
import ru.gnezdilov.service.converter.ConverterTypeTransactionModelToTypeTransactionDTO;
import ru.gnezdilov.service.dto.TypeTransactionDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeTransactionServiceTest {
    @InjectMocks TypeTransactionService subj;

    @Mock TypeTransactionDAO typeTransactionDAO;
    @Mock ConverterTypeTransactionModelToTypeTransactionDTO converter;

    @Test
    public void getAll_ListIsNotEmpty() {
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel(1, 1, "hobby");
        List<TypeTransactionModel> accountsModelsList = new ArrayList<>();
        accountsModelsList.add(typeTransactionModel);
        when(typeTransactionDAO.getAll(1)).thenReturn(accountsModelsList);

        TypeTransactionDTO typeTransactionDTO = new TypeTransactionDTO(1, 1, "hobby");
        when(converter.convert(typeTransactionModel)).thenReturn(typeTransactionDTO);

        List<TypeTransactionDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(typeTransactionDTO);
        List<TypeTransactionDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(typeTransactionDAO, times(1)).getAll(1);
        verify(converter, times(1)).convert(typeTransactionModel);
    }

    @Test
    public void getAll_ListIsEmpty() {
        List<TypeTransactionModel> transactionModelList = new ArrayList<>();
        when(typeTransactionDAO.getAll(1)).thenReturn(transactionModelList);

        List<TypeTransactionDTO> typeTransactionDTOList = new ArrayList<>();

        List<TypeTransactionDTO> type = subj.getAll(1);
        assertEquals(typeTransactionDTOList, type);

        verify(typeTransactionDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getAll_UserDAOThrowDAOException() {
        when(typeTransactionDAO.getAll(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1));

        verify(typeTransactionDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_TypeCreated() {
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel(1, 1, "hobby");
        when(typeTransactionDAO.insert(1, "hobby")).thenReturn(typeTransactionModel);

        TypeTransactionDTO typeTransactionDTO = new TypeTransactionDTO(1, 1, "hobby");
        when(converter.convert(typeTransactionModel)).thenReturn(typeTransactionDTO);

        TypeTransactionDTO type = subj.create(1, "hobby");
        assertEquals(typeTransactionDTO, type);

        verify(typeTransactionDAO, times(1))
                .insert(1, "hobby");
        verify(converter, times(1)).convert(typeTransactionModel);
    }

    @Test
    public void create_AccountCreatedFailed() {
        when(typeTransactionDAO.insert(1, "hobby"))
                .thenThrow(new DAOException("Insert type failed"));

        DAOException exception = assertThrows(DAOException.class,
                () -> subj.create(1, "hobby"));
        assertEquals("Insert type failed", exception.getMessage());

        verify(typeTransactionDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void create_AccountAlreadyExists() {
        when(typeTransactionDAO.insert(1, "hobby"))
                .thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> subj
                .create(1, "hobby"));

        verify(typeTransactionDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void create_UserDAOThrowDAOException() {
        when(typeTransactionDAO.insert(1, "hobby")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(1, "hobby"));

        verify(typeTransactionDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void delete_AccountDeleted() {
        when(typeTransactionDAO.delete(1, 1)).thenReturn(true);

        assertTrue(subj.delete(1, 1));

        verify(typeTransactionDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_AccountDeletedFailed() {
        when(typeTransactionDAO.delete(1, 1)).thenReturn(false);

        assertFalse(subj.delete(1, 1));

        verify(typeTransactionDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_UserDAOThrowDAOException() {
        when(typeTransactionDAO.delete(1, 1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.delete(1, 1));

        verify(typeTransactionDAO, times(1)).delete(1, 1);
    }

    @Test
    public void edit_AccountEdited() {
        TypeTransactionModel typeTransactionModel = new TypeTransactionModel(1, 1, "hobby");
        when(typeTransactionDAO.update(1, 1, "work")).thenReturn(typeTransactionModel);

        TypeTransactionDTO typeTransactionDTO = new TypeTransactionDTO(1, 1, "hobby");
        when(converter.convert(typeTransactionModel)).thenReturn(typeTransactionDTO);

        TypeTransactionDTO type = subj.edit(1, 1, "work");
        assertEquals(typeTransactionDTO, type);

        verify(typeTransactionDAO, times(1)).update(1, 1, "work");
        verify(converter, times(1)).convert(typeTransactionModel);
    }

    @Test
    public void edit_AccountEditedFailed() {
        when(typeTransactionDAO.update(1, 1, "work")).thenThrow(new DAOException("Not found type"));

        DAOException exception = assertThrows(DAOException.class, () -> subj.edit(1, 1, "work"));
        assertEquals("Not found type", exception.getMessage());

        verify(typeTransactionDAO, times(1)).update(1, 1, "work");
        verifyNoInteractions(converter);
    }

    @Test
    public void edit_UserDAOThrowDAOException() {
        when(typeTransactionDAO.update(1, 1, "work")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.edit(1, 1, "work"));

        verify(typeTransactionDAO, times(1)).update(1, 1, "work");
        verifyNoInteractions(converter);
    }
}