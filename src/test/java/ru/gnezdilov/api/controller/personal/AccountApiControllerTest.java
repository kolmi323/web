package ru.gnezdilov.api.controller.personal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.api.converter.ConverterAccountDTOToAccountAddResponse;
import ru.gnezdilov.api.json.DeleteRequest;
import ru.gnezdilov.api.json.account.create.AccountAddRequest;
import ru.gnezdilov.api.json.account.create.AccountAddResponse;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountApiController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
@WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
public class AccountApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @SpyBean
    private ConverterAccountDTOToAccountAddResponse converter;

    private ObjectMapper om;
    private ObjectWriter ow;

    @BeforeEach
    public void setUp() throws Exception {
        om = new ObjectMapper();
        ow = om.writer().withDefaultPrettyPrinter();

        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountDTO(1, 1, "sber", new BigDecimal(1000)));

        AccountDTO accountDTO = new AccountDTO(2, 1, "T", new BigDecimal(500));

        when(accountService.create(1, "T", new BigDecimal(500))).thenReturn(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);
    }

    @Test
    public void getShow_returnJsonListAccounts_whenCalledWithValidArguments() throws Exception{
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountDTO(1, 1, "sber", new BigDecimal(1000)));

        mockMvc.perform(get("/api/account/show"))
                .andExpect(content().json(ow.writeValueAsString(accountDTOList)))
                .andExpect(status().isOk());
    }

    @Test
    public void postDelete_whenCalledWithValidArguments() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setId(1);
        mockMvc.perform(post("/api/account/delete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(ow.writeValueAsString(deleteRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void postAdd_returnJsonAccount_whenCalledWithValidArguments() throws Exception {
        AccountAddRequest accountAddRequest = new AccountAddRequest();
        accountAddRequest.setName("T");
        accountAddRequest.setBalance(new BigDecimal(500));

        AccountAddResponse accountAddResponse = new AccountAddResponse(2,
                accountAddRequest.getName(),
                accountAddRequest.getBalance());

        mockMvc.perform(post("/api/account/add")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(ow.writeValueAsString(accountAddRequest)))
                .andExpect(content().json(ow.writeValueAsString(accountAddResponse)))
                .andExpect(status().isCreated());
    }
}