package ru.gnezdilov.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.WebApplication;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AlertController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
public class AlertControllerTest {
    @Autowired
    MockMvc mockMvc;

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