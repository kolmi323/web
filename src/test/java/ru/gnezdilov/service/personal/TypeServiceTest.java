package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TypeRepository;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceTest {
    @InjectMocks private TypeService subj;

    @Mock private TypeRepository typeRepository;
    @Mock private ConverterTypeModelToTypeDTO converter;

    @Test
    public void getAllByUserId_returnLustTypeModel_whenCalledWithValidException() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        List<TypeModel> accountsModelsList = new ArrayList<>();
        accountsModelsList.add(typeModel);
        when(typeRepository.getAllByUserId(1)).thenReturn(accountsModelsList);

        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        when(converter.convert(typeModel)).thenReturn(typeDTO);

        List<TypeDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(typeDTO);
        List<TypeDTO> accounts = subj.getAll(1);
        assertEquals(accountDTOList, accounts);

        verify(typeRepository, times(1)).getAllByUserId(1);
        verify(converter, times(1)).convert(typeModel);
    }

    @Test
    public void getAllByUserId_returnEmptyList_whenCalledWithValidException() {
        List<TypeModel> typeModelList = new ArrayList<>();
        when(typeRepository.getAllByUserId(1)).thenReturn(typeModelList);

        List<TypeDTO> typeDTOList = new ArrayList<>();
        List<TypeDTO> type = subj.getAll(1);
        assertEquals(typeDTOList, type);

        verify(typeRepository, times(1)).getAllByUserId(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getAllByUserId_acceptDAOException_whenCalledWithValidException() {
        when(typeRepository.getAllByUserId(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.getAll(1));

        verify(typeRepository, times(1)).getAllByUserId(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void save_returnTypeDTO_whenCalledWithValidException() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");
        TypeModel typeRequest = new TypeModel();
        typeRequest.setUserId(1);
        typeRequest.setName("hobby");
        when(typeRepository.save(typeRequest)).thenReturn(typeModel);

        TypeDTO typeDTO = new TypeDTO(1, 1, "hobby");
        when(converter.convert(typeModel)).thenReturn(typeDTO);

        TypeDTO type = subj.create(1, "hobby");
        assertEquals(typeDTO, type);

        verify(typeRepository, times(1)).save(typeRequest);
        verify(converter, times(1)).convert(typeModel);
    }

    @Test
    public void save_acceptDAOExceptionWithMessageAboutFailedCreated_whenCalledWithValidException() {
        TypeModel typeRequest = new TypeModel();
        typeRequest.setUserId(1);
        typeRequest.setName("hobby");
        when(typeRepository.save(typeRequest))
                .thenThrow(new DAOException("Insert type failed"));

        DAOException exception = assertThrows(DAOException.class,
                () -> subj.create(1, "hobby"));
        assertEquals("Insert type failed", exception.getMessage());

        verify(typeRepository, times(1))
                .save(typeRequest);
        verifyNoInteractions(converter);
    }

    @Test
    public void save_acceptAlreadyExistsException_whenCalledWithValidException() {
        TypeModel typeRequest = new TypeModel();
        typeRequest.setUserId(1);
        typeRequest.setName("hobby");
        when(typeRepository.save(typeRequest))
                .thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class, () -> subj
                .create(1, "hobby"));

        verify(typeRepository, times(1))
                .save(typeRequest);
        verifyNoInteractions(converter);
    }

    @Test
    public void save_acceptDAOException_whenCalledWithValidException() {
        TypeModel typeRequest = new TypeModel();
        typeRequest.setUserId(1);
        typeRequest.setName("hobby");
        when(typeRepository.save(typeRequest)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.create(1, "hobby"));

        verify(typeRepository, times(1))
                .save(typeRequest);
        verifyNoInteractions(converter);
    }

    @Test
    public void deleteByIdAndUserId_returnSuccessDeleted_whenCalledWithValidException() {
        when(typeRepository.deleteByIdAndUserId(1, 1)).thenReturn(1);

        assertTrue(subj.delete(1, 1));

        verify(typeRepository, times(1)).deleteByIdAndUserId(1, 1);
    }

    @Test
    public void deleteByIdAndUserId_returnFailedDeleted_whenCalledWithValidException() {
        when(typeRepository.deleteByIdAndUserId(1, 1)).thenReturn(0);

        assertFalse(subj.delete(1, 1));

        verify(typeRepository, times(1)).deleteByIdAndUserId(1, 1);
    }

    @Test
    public void deleteByIdAndUserId_acceptDAOException_whenCalledWithValidException() {
        when(typeRepository.deleteByIdAndUserId(1, 1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.delete(1, 1));

        verify(typeRepository, times(1)).deleteByIdAndUserId(1, 1);
    }

    @Test
    public void edit_returnTrue_whenCalledWithValidException() {
        when(typeRepository.updateName(1, 1, "work")).thenReturn(1);

        assertTrue(subj.edit(1, 1, "work"));

        verify(typeRepository, times(1)).updateName(1, 1, "work");
    }

    @Test
    public void edit_returnFalse_whenCalledWithInvalidException() {
        when(typeRepository.updateName(1, 2, "work")).thenReturn(0);

        assertFalse(subj.edit(1, 2, "work"));

        verify(typeRepository, times(1)).updateName(1, 2, "work");
    }

    @Test
    public void existsById_returnTrue_whenCalledWithValidException() {
        when(typeRepository.existsByIdAndUserId(1, 1)).thenReturn(true);

        assertTrue(subj.existsById(1, 1));

        verify(typeRepository, times(1)).existsByIdAndUserId(1, 1);
        verifyNoInteractions(converter);
    }

    @Test
    public void existsById_returnFalse_whenCalledWithInvalidException() {
        when(typeRepository.existsByIdAndUserId(1, 2)).thenReturn(false);
        assertFalse(subj.existsById(1, 2));

        verify(typeRepository, times(1)).existsByIdAndUserId(1, 2);
        verifyNoInteractions(converter);
    }

    @Test
    public void existsById_acceptDAOException_whenCalledWithValidException() {
        when(typeRepository.existsByIdAndUserId(1, 1)).thenThrow(DAOException.class);
        assertThrows(DAOException.class, () -> subj.existsById(1, 1));

        verify(typeRepository, times(1)).existsByIdAndUserId(1, 1);
        verifyNoInteractions(converter);
    }
}