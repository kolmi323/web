package ru.gnezdilov.web.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonalController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = {WebApplication.class})
@WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
public class PersonalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        when(userService.getUserById(1))
                .thenReturn(new UserDTO(1, "John", "john@mail.ru"));
    }

    @Test
    public void index_returnPersonalForm() throws Exception {
        mockMvc.perform(get("/personal"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/menu"));
    }
}