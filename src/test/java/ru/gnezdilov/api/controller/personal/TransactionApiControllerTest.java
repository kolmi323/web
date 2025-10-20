package ru.gnezdilov.api.controller.personal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.api.converter.ConverterTransactionDTOToTransactionAddResponse;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionApiController.class)
@RunWith(SpringRunner.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
public class TransactionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @SpyBean
    private ConverterTransactionDTOToTransactionAddResponse converter;


    @Before
    public void setUp() throws Exception {
        List<Integer> typeIds = new ArrayList<>();
        typeIds.add(1);

        TransactionDTO transactionDTO = new TransactionDTO(1,
                1,
                2,
                new BigDecimal("100.00"),
                LocalDate.now());

        when(transactionService.create(typeIds, 1, 1, 2, new BigDecimal("100.00")))
                .thenReturn(transactionDTO);
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postAdd_returnTransactionJson_whenCalledWithValidArguments() throws Exception {
        mockMvc.perform(post("/api/transaction/add")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\n" +
                        "\"typesIds\" : [\"1\"],\n" +
                        "\"sendingId\" : \"1\",\n" +
                        "\"receivingId\" : \"2\",\n" +
                        "\"amount\" : \"100.00\"" +
                        "}"))
                .andExpect(content().json("{\n" +
                        "  id : 1,\n" +
                        "  amount : 100\n" +
                        "}"))
                .andExpect(status().isCreated());
    }
}