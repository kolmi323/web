package ru.gnezdilov.web.controller.start;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.gnezdilov.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = LoginController.class)
public class LoginControllerTest extends AbstractControllerTest {
    @Test
    public void login_returnLoginFrom() throws Exception {
        mockMvc.perform(get("/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }
}