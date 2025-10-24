package ru.gnezdilov.api.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.gnezdilov.AbstractControllerTest;
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
public class ReportTransactionApiControllerTest extends AbstractControllerTest {
    @MockBean
    private CategoryTransactionService categoryTransactionService;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        Map<String, BigDecimal> incomingTransactions = new HashMap<>();
        incomingTransactions.put("work", new BigDecimal("1000.00"));

        Map<String, BigDecimal> outgoingTransactions = new HashMap<>();
        outgoingTransactions.put("hobby", new BigDecimal("200.00"));

        when(categoryTransactionService.getIncomingReport(
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31))).thenReturn(incomingTransactions);
        when(categoryTransactionService.getOutgoingReport(
                1,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31))).thenReturn(outgoingTransactions);
    }

    @Test
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
    public void getGetIncomingTransaction_returnNull_whenCalledWithNullArgument() throws Exception {
        mockMvc.perform(get("/api/report/incoming")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{" +
                                "\"dateAfter\" : \"\",\n" +
                                "\"dateBefore\" : \"2025-12-31\"\n" +
                                "}"))
                .andExpect(content().json("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getGetIncomingTransaction_return400_whenCalledWithWrongTypeArgument() throws Exception {
        mockMvc.perform(get("/api/report/incoming")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{" +
                                "\"dateAfter\" : \"start year\",\n" +
                                "\"dateBefore\" : \"2025-12-31\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
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

    @Test
    public void getGetOutgoingTransaction_returnNull_whenCalledWithNullArgument() throws Exception {
        mockMvc.perform(get("/api/report/outgoing")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{" +
                                "\"dateAfter\" : \"\",\n" +
                                "\"dateBefore\" : \"2025-12-31\"\n" +
                                "}"))
                .andExpect(content().json("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getGetOutgoingTransaction_return400_whenCalledWithWrongTypeArgument() throws Exception {
        mockMvc.perform(get("/api/report/outgoing")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{" +
                                "\"dateAfter\" : \"start year\",\n" +
                                "\"dateBefore\" : \"2025-12-31\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}