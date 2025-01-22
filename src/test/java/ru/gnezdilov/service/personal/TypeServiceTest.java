package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TypeDAO;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.model.TypeModel;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
    @InjectMocks TypeService subj;

    @Mock TypeDAO typeDAO;
    @Mock ConverterTypeModelToTypeDTO converter;

    @Test
    public void getAll_returnLustTypeModel_whenCalledWithValidException() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        List<TypeModel> accountsModelsList = new ArrayList<>();
        accountsModelsList.add(typeModel);
        when(typeDAO.getAll(1)).thenReturn(accountsModelsList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        when(converter.convert(typeModel)).thenReturn(typeDTO);

        List<TypeDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(typeDTO);
        List<TypeDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(typeDAO, times(1)).getAll(1);
        verify(converter, times(1)).convert(typeModel);
    }

    @Test
    public void getAll_returnEmptyList_whenCalledWithValidException() {
        List<TypeModel> typeModelList = new ArrayList<>();
        when(typeDAO.getAll(1)).thenReturn(typeModelList);

        List<TypeDTO> typeDTOList = new ArrayList<>();
        List<TypeDTO> type = subj.getAll(1);
        assertEquals(typeDTOList, type);

        verify(typeDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getAll_acceptDAOException_whenCalledWithValidException() {
        when(typeDAO.getAll(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1));

        verify(typeDAO, times(1)).getAll(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void create_returnTypeDTO_whenCalledWithValidException() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        when(typeDAO.insert(1, "hobby")).thenReturn(typeModel);

        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        when(converter.convert(typeModel)).thenReturn(typeDTO);

        TypeDTO type = subj.create(1, "hobby");
        assertEquals(typeDTO, type);

        verify(typeDAO, times(1))
                .insert(1, "hobby");
        verify(converter, times(1)).convert(typeModel);
    }

    @Test
    public void create_acceptDAOExceptionWithMessageAboutFailedCreated_whenCalledWithValidException() {
        when(typeDAO.insert(1, "hobby"))
                .thenThrow(new DAOException("Insert type failed"));

        DAOException exception = assertThrows(DAOException.class,
                () -> subj.create(1, "hobby"));
        assertEquals("Insert type failed", exception.getMessage());

        verify(typeDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptAlreadyExistsException_whenCalledWithValidException() {
        when(typeDAO.insert(1, "hobby"))
                .thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> subj
                .create(1, "hobby"));

        verify(typeDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void create_acceptDAOException_whenCalledWithValidException() {
        when(typeDAO.insert(1, "hobby")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(1, "hobby"));

        verify(typeDAO, times(1))
                .insert(1, "hobby");
        verifyNoInteractions(converter);
    }

    @Test
    public void delete_returnSuccessDeleted_whenCalledWithValidException() {
        when(typeDAO.delete(1, 1)).thenReturn(true);

        assertTrue(subj.delete(1, 1));

        verify(typeDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_returnFailedDeleted_whenCalledWithValidException() {
        when(typeDAO.delete(1, 1)).thenReturn(false);

        assertFalse(subj.delete(1, 1));

        verify(typeDAO, times(1)).delete(1, 1);
    }

    @Test
    public void delete_acceptDAOException_whenCalledWithValidException() {
        when(typeDAO.delete(1, 1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.delete(1, 1));

        verify(typeDAO, times(1)).delete(1, 1);
    }

    @Test
    public void edit_returnEditedTypeDTO_whenCalledWithValidException() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        when(typeDAO.update(1, 1, "work")).thenReturn(typeModel);

        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        when(converter.convert(typeModel)).thenReturn(typeDTO);

        TypeDTO type = subj.edit(1, 1, "work");
        assertEquals(typeDTO, type);

        verify(typeDAO, times(1)).update(1, 1, "work");
        verify(converter, times(1)).convert(typeModel);
    }

    @Test
    public void edit_acceptDAOExceptionWithMessageAboutNotFoundType_whenCalledWithValidException() {
        when(typeDAO.update(1, 1, "work")).thenThrow(new DAOException("Not found type"));

        DAOException exception = assertThrows(DAOException.class, () -> subj.edit(1, 1, "work"));
        assertEquals("Not found type", exception.getMessage());

        verify(typeDAO, times(1)).update(1, 1, "work");
        verifyNoInteractions(converter);
    }

    @Test
    public void edit_acceptDAOException_whenCalledWithValidException() {
        when(typeDAO.update(1, 1, "work")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.edit(1, 1, "work"));

        verify(typeDAO, times(1)).update(1, 1, "work");
        verifyNoInteractions(converter);
    }
}