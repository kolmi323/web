package ru.gnezdilov.web.controller.start;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.WebApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = LoginController.class)
@ContextConfiguration(classes = WebApplication.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login_returnLoginFrom() throws Exception {
        mockMvc.perform(get("/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }
}