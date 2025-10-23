package ru.gnezdilov.web.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import ru.gnezdilov.AbstractControllerTest;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest extends AbstractControllerTest {
    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

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
    public void transaction_returnMainTransactionForm() throws Exception {
        mockMvc.perform(get("/transaction"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/transaction/main"));
    }

    @Test
    public void getAddTransaction_returnTransactionAddForm() throws Exception {
        mockMvc.perform(get("/transaction/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/transaction/add"));
    }

    @Test
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
    public void postAddTransaction_returnTransactionAddForm_whenCalledWithNull() throws Exception {
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

    @Test
    public void postAddTransaction_returnErrorForm_whenCalledWithInvalidFormat() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("typeIds", "one");
        params.add("sendingId", "1");
        params.add("receivingId", "2");
        params.add("amount", "100");

        mockMvc.perform(post("/transaction/add")
                        .params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertError"));
    }

    @Test
    public void postAddTransaction_returnErrorForm_whenAccountNotFound() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("typeIds", "one");
        params.add("sendingId", "1");
        params.add("receivingId", "10");
        params.add("amount", "100");

        mockMvc.perform(post("/transaction/add")
                        .params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertError"));
    }
}