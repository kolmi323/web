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
import org.springframework.util.LinkedMultiValueMap;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ReportController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
@WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
public class ReportControllerTest {
    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private CategoryTransactionService categoryTransactionService;


    @BeforeEach
    public void setUp() throws Exception {
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
    public void report_returnMainReportForm() throws Exception {
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/main"));
    }

    @Test
    public void getReportIncomingData_returnReportIncomingDataForm() throws Exception {
        mockMvc.perform(get("/report/incoming"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/data"));
    }

    @Test
    public void postReportIncomingData_redirectToReportShow_whenCalledWithValidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateAfter", "2025-01-01");
        params.add("dateBefore", "2025-12-31");

        mockMvc.perform(post("/report/incoming").params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/report/show"));
    }

    @Test
    public void postReportIncomingData_returnReportIncomingForm_whenCalledWithInvalidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateAfter", "");
        params.add("dateBefore", "2025-12-31");

        mockMvc.perform(post("/report/incoming").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/data"));
    }

    @Test
    public void getReportOutgoingData_returnReportOutgoingDataForm() throws Exception {
        mockMvc.perform(get("/report/outgoing"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/data"));
    }

    @Test
    public void postReportOutgoingData_redirectToReportShow_whenCalledWithValidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateAfter", "2025-01-01");
        params.add("dateBefore", "2025-12-31");

        mockMvc.perform(post("/report/outgoing").params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/report/show"));
    }

    @Test
    public void postReportOutgoingData_returnReportOutgoingForm_whenCalledWithInvalidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("dateAfter", "");
        params.add("dateBefore", "2025-12-31");

        mockMvc.perform(post("/report/outgoing").params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/data"));
    }

    @Test
    public void showReport_returnIncomingTransactionOnReportShowForm() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("dateAfter", "2025-01-01");
        attributes.put("dateBefore", "2025-12-31");
        attributes.put("typeReport", "incoming");

        mockMvc.perform(get("/report/show")
                        .sessionAttrs(attributes))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/show"));
    }

    @Test
    public void showReport_returnOutgoingTransactionOnReportShowForm() throws Exception {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("dateAfter", "2025-01-01");
        attributes.put("dateBefore", "2025-12-31");
        attributes.put("typeReport", "outgoing");

        mockMvc.perform(get("/report/show")
                        .sessionAttrs(attributes))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/report/show"));
    }
}