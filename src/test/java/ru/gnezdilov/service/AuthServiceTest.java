package ru.gnezdilov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.exception.DAOException;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.service.converter.ConverterUserModelToUserDTO;
import ru.gnezdilov.service.custominterface.DigestService;
import ru.gnezdilov.service.dto.UserDTO;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    @InjectMocks private AuthService subj;

    @Mock private UserDAO userDAO;
    @Mock private DigestService digestService;
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
        when(userDAO.findUserByEmailAndPassword("anton@mail.ru", "hash")).thenReturn(userModel);

        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        when(converter.convert(userModel.get())).thenReturn(userDTO);

        UserDTO user = subj.authorization("anton@mail.ru", "password");
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).findUserByEmailAndPassword("anton@mail.ru", "hash");
        verify(converter, times(1)).convert(userModel.get());
    }

    @Test
    public void authorization_acceptNotFoundException_whenCalledWithInvalidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");
        when(userDAO.findUserByEmailAndPassword("anton@mail.ru", "hash")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> subj.authorization("anton@mail.ru", "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).findUserByEmailAndPassword("anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void authorization_acceptDAOException_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");
        when(userDAO.findUserByEmailAndPassword("anton@mail.ru", "hash")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.authorization("anton@mail.ru", "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).findUserByEmailAndPassword("anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void createNewUser_returnUserDTO_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        UserModel userModel = new UserModel();
        userModel.setName("Anton");
        userModel.setEmail("anton@mail.ru");
        userModel.setPassword("hash");

        when(userDAO.insert("Anton", "anton@mail.ru", "hash")).thenReturn(userModel);

        UserDTO userDTO = new UserDTO(1, "Anton", "anton@mail.ru");
        when(converter.convert(userModel)).thenReturn(userDTO);

        UserDTO user = subj.createNewUser("Anton", "anton@mail.ru", "password");
        assertEquals(userDTO, user);

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).insert("Anton", "anton@mail.ru", "hash");
        verify(converter, times(1)).convert(userModel);
    }

    @Test
    public void createNewUser_acceptDAOExceptionWithMessageAboutFailedCreated_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        DAOException daoException = new DAOException("Insert user failed");
        when(userDAO.insert("Anton", "anton@mail.ru", "hash")).thenThrow(daoException);

        DAOException exception = assertThrows(DAOException.class,
                () -> subj.createNewUser("Anton", "anton@mail.ru", "password"));
        assertEquals("Insert user failed", exception.getMessage());

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).insert("Anton", "anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void createNewUser_acceptAlreadyExistsException_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");

        when(userDAO.insert("Anton", "anton@mail.ru", "hash"))
                .thenThrow(AlreadyExistsException.class);

        assertThrows(AlreadyExistsException.class,
                () -> subj.createNewUser("Anton", "anton@mail.ru", "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).insert("Anton", "anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }

    @Test
    public void createNewUser_acceptDAOException_whenCalledWithValidArguments() {
        when(digestService.hashPassword("password")).thenReturn("hash");
        when(userDAO.insert("Anton", "anton@mail.ru", "hash")).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.createNewUser("Anton", "anton@mail.ru", "password"));

        verify(digestService, times(1)).hashPassword("password");
        verify(userDAO, times(1)).insert("Anton", "anton@mail.ru", "hash");
        verifyNoInteractions(converter);
    }
}