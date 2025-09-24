package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.TypeRepository;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.service.dto.TypeDTO;

import javax.persistence.EntityNotFoundException;
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
    public void getAllByUserId_returnListTypeModel_whenCalledWithValidException() {
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
    public void getAllByUserId_returnEmptyList_whenCalledWithValidArguments() {
        List<TypeModel> typeModelList = new ArrayList<>();
        when(typeRepository.getAllByUserId(1)).thenReturn(typeModelList);

        List<TypeDTO> typeDTOList = new ArrayList<>();
        List<TypeDTO> type = subj.getAll(1);
        assertEquals(typeDTOList, type);

        verify(typeRepository, times(1)).getAllByUserId(1);
        verifyNoInteractions(converter);
    }

    @Test
    public void getModelByID_returnTypeDTO_whenCalledWithValidArguments() {
        TypeModel typeModel = new TypeModel(1, 1, "hobby");

        when(typeRepository.getOne(1)).thenReturn(typeModel);

        assertEquals(typeModel, subj.getModelById(1));
        verify(typeRepository, times(1)).getOne(1);
    }

    @Test
    public void getModelById_acceptEntityNotFoundException_whenCalledWithValidArguments() {
        when(typeRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> subj.getModelById(1));

        verify(typeRepository, times(1)).getOne(1);
    }

    @Test
    public void create_returnTypeDTO_whenCalledWithValidException() {
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
    public void save_acceptIllegalArgumentException_whenCalledWithInvalidException() {
        TypeModel typeRequest = new TypeModel();
        typeRequest.setUserId(1);
        when(typeRepository.save(typeRequest)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.create(1, null));

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
    public void edit_returnTypeDTO_whenCalledWithValidException() {
        TypeModel typeRequest = new TypeModel(1, 1, "hobby");
        TypeModel typeUpdate = new TypeModel(1, 1, "work");
        TypeDTO typeDTO = new TypeDTO(1, 1, "work");

        when(typeRepository.findByIdAndUserId(1, 1)).thenReturn(typeRequest);
        typeUpdate.setName("work");
        when(typeRepository.save(typeRequest)).thenReturn(typeUpdate);
        when(converter.convert(typeUpdate)).thenReturn(typeDTO);

        assertEquals(typeDTO, subj.edit(1, 1, "hobby"));

        verify(typeRepository, times(1)).findByIdAndUserId(1, 1);
        verify(typeRepository, times(1)).save(typeRequest);
        verify(converter, times(1)).convert(typeUpdate);
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
}