package ru.gnezdilov.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.dao.entities.UserModel;
import ru.gnezdilov.security.UserRole;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.gnezdilov.security.UserRole.USER;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository subj;

    private UserModel user;

    @Before
    public void setUp() {
        user = new UserModel(1, "John", "john@mail.ru",
                "$2a$10$bN9KZDnhNu3Lu66aCQrH6usaN9giUjIfJE.RI3dCGHCC61vhtUhNO");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(USER);
        user.setRoles(userRoles);
    }

    @Test
    public void findByEmailAndPassword_returnUserModel_whenCalledWithValidArguments() {
        UserModel userModel = subj.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();

        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getEmail(), userModel.getEmail());
        assertEquals(user.getName(), userModel.getName());
    }

    @Test
    public void findByEmail_returnUserModel_whenCalledWithValidArguments() {
        UserModel userModel = subj.findByEmail("john@mail.ru");

        assertEquals(user.getName(), userModel.getName());
        assertEquals(user.getEmail(), userModel.getEmail());
        assertEquals(user.getName(), userModel.getName());
    }

    @Test
    public void existsById_returnTrue_whenCalledWithValidArguments() {
        assertTrue(subj.existsById(user.getId()));
    }
}