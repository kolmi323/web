package ru.gnezdilov.api.controller.personal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.personal.CategoryTransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportTransactionApiController.class)
@RunWith(SpringRunner.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
public class ReportTransactionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryTransactionService categoryTransactionService;

    private ObjectMapper om;
    private ObjectWriter ow;

    @Before
    public void setUp() throws Exception {
        om = new ObjectMapper();
        ow = om.writer().withDefaultPrettyPrinter();

        Map<String, BigDecimal> incomingTransactions = new HashMap<>();
        incomingTransactions.put("work", new BigDecimal("1000.00"));

        Map<String, BigDecimal> outgoingTransactions = new HashMap<>();
        outgoingTransactions.put("hobby", new BigDecimal("200.00"));

        when(categoryTransactionService.getIncomingTransactions(
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31))).thenReturn(incomingTransactions);
        when(categoryTransactionService.getOutgoingTransactions(
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31))).thenReturn(outgoingTransactions);
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getGetIncomingTransaction_returnsIncomingTransaction_whenCalledWithValidArguments() throws Exception {
        Map<String, BigDecimal> incomingTransactions = new HashMap<>();
        incomingTransactions.put("work", new BigDecimal("1000.00"));

        mockMvc.perform(get("/api/report/incoming")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{" +
                            "\"dateAfter\" : \"2025-01-01\",\n" +
                            "\"dateBefore\" : \"2025-12-31\"\n" +
                            "}"))
                .andExpect(content().json(ow.writeValueAsString(incomingTransactions)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postGetOutgoingTransaction_returnOutgoingTransaction_whenCalledWithValidArguments() throws Exception {
        Map<String, BigDecimal> outgoingTransactions = new HashMap<>();
        outgoingTransactions.put("hobby", new BigDecimal("200.00"));

        mockMvc.perform(get("/api/report/outgoing")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "\"dateAfter\" : \"2025-01-01\",\n" +
                                "\"dateBefore\": \"2025-12-31\"\n" +
                                "}"))
                .andExpect(content().json(ow.writeValueAsString(outgoingTransactions)))
                .andExpect(status().isOk());
    }
}