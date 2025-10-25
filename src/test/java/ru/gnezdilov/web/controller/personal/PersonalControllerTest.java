package ru.gnezdilov.web.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gnezdilov.AbstractControllerTest;
import ru.gnezdilov.service.dto.UserDTO;
import ru.gnezdilov.service.personal.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonalController.class)
public class PersonalControllerTest extends AbstractControllerTest {
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

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