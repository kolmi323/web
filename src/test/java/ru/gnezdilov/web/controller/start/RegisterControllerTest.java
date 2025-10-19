package ru.gnezdilov.web.controller.start;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = RegisterController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class RegisterControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Before
    public void setUp() throws Exception {
        UserDTO userDTO = new UserDTO(1, "John", "john@mail.ru");

        when(authService.createNewUser(
                "John", "john@mail.ru", "john"
        )).thenReturn(userDTO);
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
        request.add("name", "John");
        request.add("email", "john@mail.ru");
        request.add("password", "john");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    public void postRegister_returnRegisterForm_whenCalledWithInvalidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "");
        request.add("email", "john@mail.ru");
        request.add("password", "john");
        mockMvc.perform(post("/register").params(request))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }
}