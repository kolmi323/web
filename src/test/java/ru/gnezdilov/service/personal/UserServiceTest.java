package ru.gnezdilov.service.personal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gnezdilov.dao.UserDAO;
import ru.gnezdilov.dao.exception.DAOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks UserService subj;

    @Mock UserDAO userDao;

    @Test
    public void existsById_returnTrue_whenCalledWithValidArguments() {
        when(userDao.existsById(1)).thenReturn(true);

        assertTrue(subj.existsById(1));
        verify(userDao, times(1)).existsById(1);
    }

    @Test
    public void existsById_returnFalse_whenCalledWithInvalidArguments() {
        when(userDao.existsById(1)).thenReturn(false);

        assertFalse(subj.existsById(1));
        verify(userDao, times(1)).existsById(1);
    }

    @Test
    public void existsById_acceptDaoException_whenCalledWithValidArguments() {
        when(userDao.existsById(1)).thenThrow(DAOException.class);

        assertThrows(DAOException.class, () -> subj.existsById(1));
        verify(userDao, times(1)).existsById(1);
    }
}