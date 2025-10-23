package ru.gnezdilov.api.controller.start;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.AbstractControllerTest;
import ru.gnezdilov.api.converter.ConverterUserDTOToRegisterResponse;
import ru.gnezdilov.api.json.register.RegisterRequest;
import ru.gnezdilov.api.json.register.RegisterResponse;
import ru.gnezdilov.service.AuthService;
import ru.gnezdilov.service.dto.UserDTO;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StartApiController.class)
@ContextConfiguration(classes = WebApplication.class)
public class StartApiControllerTest extends AbstractControllerTest {
    @MockBean
    private AuthService authService;

    @SpyBean
    private ConverterUserDTOToRegisterResponse converter;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        when(authService.createNewUser("anton", "anton@mail.ru", "anton"))
                .thenReturn(new UserDTO(3, "anton", "anton@mail.ru"));

        when(authService.createNewUser("John", "john@mail.ru", "john"))
                .thenThrow(this.constraintViolationSQLAlreadyExistException);
    }

    @Test
    public void registerApi_returnCorrectJson_whenCalledWithValidArguments() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("anton");
        registerRequest.setEmail("anton@mail.ru");
        registerRequest.setPassword("anton");

        RegisterResponse registerResponse = new RegisterResponse(3, "anton@mail.ru");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(registerRequest)))
                .andExpect(content().json(ow.writeValueAsString(registerResponse)))
                .andExpect(status().isCreated());
    }

    @Test
    public void registerApi_return400_whenEntityAlreadyExists() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setEmail("john@mail.ru");
        registerRequest.setPassword("john");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void registerApi_return400_whenCalledWithNullArgument() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setEmail("john@mail.ru");
        registerRequest.setPassword("john");

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest());
    }
}