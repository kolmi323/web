package ru.gnezdilov.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gnezdilov.dao.UserRepository;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.dao.exception.IllegalArgumentException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.dto.UserDTO;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks private AuthService subj;

    @Mock private UserRepository userRepository;
    @Mock private BCryptPasswordEncoderUtils digestService;
    @Mock private ConverterUserModelToUserDTO converter;

    @Test
    public void authorization_returnUserDTO_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        UserModel userBuffer = new UserModel();
        userBuffer.setId(1);
        userBuffer.setName("Anton");
        userBuffer.setEmail("anton@mail.ru");
        userBuffer.setPassword("hash");
        Optional<UserModel> userModel = Optional.of(userBuffer);
        when(userRepository.findByEmailAndPassword("anton@mail.ru", "hash")).thenReturn(userModel);

        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        when(converter.convert(userModel.get())).thenReturn(userDTO);

        UserDTO user = subj.authorization("anton@mail.ru", "password");
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hashPassword("password");
        verify(userRepository, times(1)).findByEmailAndPassword("anton@mail.ru", "hash");
        verify(converter, times(1)).convert(userModel.get());
    }

    @Test
    public void authorization_acceptNotFoundException_whenCalledWithInvalidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");
        when(userRepository.findByEmailAndPassword("anton@mail.ru", "hash")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> subj.authorization("anton@mail.ru", "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userRepository, times(1)).findByEmailAndPassword("anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void authorization_acceptIllegalArgumentException_whenCalledWithInvalidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");
        when(userRepository.findByEmailAndPassword(null, "hash")).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.authorization(null, "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userRepository, times(1)).findByEmailAndPassword(null, "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void createNewUser_returnUserDTO_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        UserModel userRequest = new UserModel();
        userRequest.setName("Anton");
        userRequest.setEmail("anton@mail.ru");
        userRequest.setPassword("hash");

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setName("Anton");
        userModel.setEmail("anton@mail.ru");
        userModel.setPassword("hash");

        when(userRepository.save(userRequest)).thenReturn(userModel);

        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        when(converter.convert(userModel)).thenReturn(userDTO);

        UserDTO user = subj.createNewUser("Anton", "anton@mail.ru", "password");
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hashPassword("password");
        verify(userRepository, times(1)).save(userRequest);
        verify(converter, times(1)).convert(userModel);
    }

    @Test
    public void createNewUser_acceptIllegalArgumentException_whenCalledWithInvalidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        UserModel userRequest = new UserModel();
        userRequest.setPassword("hash");

        when(userRepository.save(userRequest)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> subj.createNewUser(null, null, "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userRepository, times(1)).save(userRequest);
        verifyNoInteractions(converter);
    }
}