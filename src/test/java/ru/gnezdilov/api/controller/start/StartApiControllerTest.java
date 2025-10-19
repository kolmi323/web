package ru.gnezdilov.api.controller.start;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.WebApplication;
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
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class StartApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @SpyBean
    private ConverterUserDTOToRegisterResponse converter;


    @Before
    public void setUp() throws Exception {
        when(authService.createNewUser("anton", "anton@mail.ru", "anton"))
                .thenReturn(new UserDTO(3, "anton", "anton@mail.ru"));
    }

    @Test
    public void register() throws Exception {
        ObjectMapper om = new ObjectMapper();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();

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
}