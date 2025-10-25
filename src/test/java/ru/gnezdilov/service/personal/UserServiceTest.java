package ru.gnezdilov.service.personal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.dto.UserDTO;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks UserService subj;

    @Mock UserRepository userRepository;
    @Mock ConverterUserModelToUserDTO converter;

    @Test
    public void getUserById_returnUserDTO_whenCalledWithValidArguments() {
        UserModel userModel = new UserModel(1, "test", "test@mail.ru", "testPassword");
        when(userRepository.getOne(1)).thenReturn(userModel);

        UserDTO userDTO = new UserDTO(1, "test", "test@mail.ru");
        when(converter.convert(userModel)).thenReturn(userDTO);

        UserDTO user = new UserDTO(1, "test", "test@mail.ru");
        assertEquals(user, subj.getUserById(1));
        verify(userRepository, times(1)).getOne(1);
        verify(converter, times(1)).convert(userModel);
    }

    @Test
    public void getUserById_throwEntityNotFoundException_whenCalledWithInvalidArguments() {
        when(userRepository.getOne(2)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> subj.getUserById(2));

        verify(userRepository, times(1)).getOne(2);
        verifyNoMoreInteractions(converter);
    }

    @Test
    public void existsById_returnTrue_whenCalledWithValidArguments() {
        when(userRepository.existsById(1)).thenReturn(true);

        assertTrue(subj.existsById(1));
        verify(userRepository, times(1)).existsById(1);
    }

    @Test
    public void existsById_returnFalse_whenCalledWithInvalidArguments() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertFalse(subj.existsById(1));
        verify(userRepository, times(1)).existsById(1);
    }
}