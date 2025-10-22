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

@WebMvcTest(StartController.class)
@ContextConfiguration(classes = WebApplication.class)
public class StartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void start_returnStartForm() throws Exception {
        mockMvc.perform(get("/start"))
                .andExpect(status().isOk())
                .andExpect(view().name("start"));
    }
}