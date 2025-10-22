package ru.gnezdilov.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.WebApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlertController.class)
@ContextConfiguration(classes = WebApplication.class)
public class AlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void error_returnErrorForm() throws Exception {
        mockMvc.perform(get("/alertError?error=superWarningError"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "superWarningError"))
                .andExpect(view().name("error"));
    }

    @Test
    public void message_returnMessageForm() throws Exception {
        mockMvc.perform(get("/alertMessage?message=superImportantMessage"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("message", "superImportantMessage"))
                .andExpect(view().name("message"));
    }
}