package ru.gnezdilov.web.controller.personal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TransactionController.class)
@RunWith(SpringRunner.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @Before
    public void setUp() throws Exception {
        List<Integer> typeIds = new ArrayList<>();
        typeIds.add(1);
        typeIds.add(2);

        TransactionDTO transactionDTO = new TransactionDTO(1,
                1,
                2,
                new BigDecimal(100),
                LocalDate.of(2025, 10, 20));

        when(transactionService.create(typeIds, 1, 1, 2, new BigDecimal(100)))
                .thenReturn(transactionDTO);
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void transaction_returnMainTransactionForm() throws Exception {
        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/transaction/main"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getAddTransaction_returnTransactionAddForm() throws Exception {
        mockMvc.perform(get("/transaction/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/transaction/add"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postAddTransaction_redirectToMessageForm_whenCalledWithValidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("typeIds", "1,2");
        params.add("sendingId", "1");
        params.add("receivingId", "2");
        params.add("amount", "100");

        mockMvc.perform(post("/transaction/add")
                        .params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postAddTransaction_returnTransactionAddForm_whenCalledWithInvalidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("typeIds", "1, 2");
        params.add("sendingId", "");
        params.add("receivingId", "2");
        params.add("amount", "100");

        mockMvc.perform(post("/transaction/add")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/transaction/add"));

    }
}