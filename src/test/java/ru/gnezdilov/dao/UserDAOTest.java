package ru.gnezdilov.dao;

import org.junit.Test;
import ru.gnezdilov.dao.exception.AlreadyExistsException;
import ru.gnezdilov.dao.model.UserModel;

import java.util.Optional;

import static org.junit.Assert.*;

/*public class UserDAOTest extends AbstractDAOTest<UserDAO> {
    public UserDAOTest() {
        setPropertyForConnectH2();
        subj = DaoFactory.getInstance().getUserDAO();
    }

    @Test
    public void insert_successCreateAndReturnUserModel_whenCalledWithValidArguments() {
        UserModel userModel = new UserModel(3, "anton", "anton@mail.ru", "784742a66a3a0c271feced5b149ff8db");
        UserModel user = subj.insert("anton", "anton@mail.ru", "784742a66a3a0c271feced5b149ff8db");

        assertEquals(userModel, user);
    }

    @Test (expected = AlreadyExistsException.class)
    public void insert_failedCreateAndThrowAlreadyExistsException_whenCalledWithValidArguments() {
        UserModel user = subj.insert("John Doe", "john@mail.ru", "527bd5b5d689e2c32ae974c6229ff785");
    }

    @Test
    public void findByEmailAndPassword_returnUser_whenCalledWithValidArguments() {
        Optional<UserModel> userOptional = subj.findByEmailAndPassword("john@mail.ru", "527bd5b5d689e2c32ae974c6229ff785");

        UserModel user = userOptional.get();
        assertEquals(userOptional.get(), user);
    }

    @Test
    public void findByEmailAndPassword_returnEmptyUser_whenCalledWithInvalidArguments() {
        Optional<UserModel> userOptional = subj.findByEmailAndPassword("john@mail.ru", null);

        assertFalse(userOptional.isPresent());
    }

    @Test
    public void existsById_returnTrue_whenCalledWithValidArguments() {
        assertTrue(subj.existsById(1));
    }

    @Test
    public void existsById_returnFalse_whenCalledWithInvalidArguments() {
        assertFalse(subj.existsById(3));
    }
}*/
