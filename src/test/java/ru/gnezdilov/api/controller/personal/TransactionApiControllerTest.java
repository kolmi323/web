package ru.gnezdilov.api.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import ru.gnezdilov.AbstractControllerTest;
import ru.gnezdilov.api.converter.ConverterTransactionDTOToTransactionAddResponse;
import ru.gnezdilov.api.json.transaction.add.TransactionAddRequest;
import ru.gnezdilov.api.json.transaction.add.TransactionAddResponse;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.service.personal.TransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionApiController.class)
public class TransactionApiControllerTest extends AbstractControllerTest {
    @MockBean
    private TransactionService transactionService;

    @SpyBean
    private ConverterTransactionDTOToTransactionAddResponse converter;


    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

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
    public void postAdd_returnTransactionJson_whenCalledWithValidArguments() throws Exception {
        TransactionAddRequest transactionAddRequest = new TransactionAddRequest();
        transactionAddRequest.setTypesIds(singletonList(1));
        transactionAddRequest.setSendingId(1);
        transactionAddRequest.setReceivingId(2);
        transactionAddRequest.setAmount(new BigDecimal("100.00"));

        TransactionAddResponse transactionAddResponse = new TransactionAddResponse(1, new BigDecimal("100.00"));
        mockMvc.perform(post("/api/transaction/add")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ow.writeValueAsString(transactionAddRequest)))
                .andExpect(content().json(ow.writeValueAsString(transactionAddResponse)))
                .andExpect(status().isCreated());
    }

    @Test
    public void postAdd_return400_whenCalledWithNullArguments() throws Exception {
        TransactionAddRequest transactionAddRequest = new TransactionAddRequest();
        transactionAddRequest.setTypesIds(singletonList(null));
        transactionAddRequest.setSendingId(1);
        transactionAddRequest.setReceivingId(2);
        transactionAddRequest.setAmount(new BigDecimal("100.00"));

        mockMvc.perform(post("/api/transaction/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(transactionAddRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postAdd_return400_whenCalledWithWrongTypeArguments() throws Exception {
        mockMvc.perform(post("/api/transaction/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\n" +
                                "    \"typesIds\" : [\"1\"],\n" +
                                "    \"sendingId\" : \"one\",\n" +
                                "    \"receivingId\" : \"3\",\n" +
                                "    \"amount\" : \"450.00\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}