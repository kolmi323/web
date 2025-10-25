package ru.gnezdilov.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.gnezdilov.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlertController.class)
public class AlertControllerTest extends AbstractControllerTest {
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