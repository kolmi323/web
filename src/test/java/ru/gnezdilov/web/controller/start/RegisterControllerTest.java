package ru.gnezdilov.web.controller.start;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = RegisterController.class)
@ContextConfiguration(classes = WebApplication.class)
public class RegisterControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @BeforeEach
    public void setUp() throws Exception {
        UserDTO userDTO = new UserDTO(1, "John", "john@mail.ru");
        UserDTO userException = new UserDTO(1, "exception", "exception");
        SQLException sqlException = new SQLException("repeat recording", "23505");
        Exception e = new ConstraintViolationException("Such a record already exists", sqlException, "repeat recording");


        when(authService.createNewUser(
                "John", "john@mail.ru", "john"
        )).thenThrow(e);

        when(authService.createNewUser(
                "Artur", "artur@mail.ru", "artur"
        )).thenReturn(userDTO);

        when(authService.createNewUser(
                "", "artur@mail.ru", "artur"
        )).thenReturn(userException);

        when(authService.createNewUser(
                "John", "jo.ru", "john"
        )).thenReturn(userException);
    }

    @Test
    public void getRegister_returnRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void postRegister_returnMessageForm_whenCalledWithValidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "Artur");
        request.add("email", "artur@mail.ru");
        request.add("password", "artur");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    public void postRegister_returnErrorForm_whenCalledWithInvalidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "John");
        request.add("email", "john@mail.ru");
        request.add("password", "john");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertError"));
    }

    @Test
    public void postRegister_returnRegisterForm_whenCalledWithInvalidArgumentNull() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "");
        request.add("email", "john@mail.ru");
        request.add("password", "john");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void postRegister_returnRegisterForm_whenCalledWithInvalidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "john");
        request.add("email", "jo.ru");
        request.add("password", "john");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}