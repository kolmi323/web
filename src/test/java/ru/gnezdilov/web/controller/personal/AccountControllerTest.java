package ru.gnezdilov.web.controller.personal;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.gnezdilov.security.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = AccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = {WebApplication.class})
@WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    public void setUp() throws Exception {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountDTO(1, 1, "sber", new BigDecimal(1000)));
        SQLException sqlException = new SQLException("repeat recording", "23505");
        Exception e = new ConstraintViolationException("Such a record already exists", sqlException, "repeat recording");

        AccountDTO accountDTO = new AccountDTO(2, 1, "T", new BigDecimal(500));

        when(accountService.create(1, "T", new BigDecimal(500))).thenReturn(accountDTO);
        when(accountService.create(1, "sber", new BigDecimal(1000))).thenThrow(e);
        when(accountService.getAll(1)).thenReturn(accountDTOList);
    }

    @Test
    public void account_returnMainAccountForm() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/main"));
    }

    @Test
    public void showAccount_returnAccountShowForm() throws Exception {
        mockMvc.perform(get("/account/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/show"));
    }

    @Test
    public void getAddAccount_returnAddAccountForm() throws Exception {
        mockMvc.perform(get("/account/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/add"));
    }

    @Test
    public void postAddAccount_returnMessageForm_whenCalledWithValidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "T");
        request.add("balance", "500");

        mockMvc.perform(post("/account/add")
                        .params(request))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    public void postAddAccount_returnErrorForm_whenCalledWithInvalidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "sber");
        request.add("balance", "1000");

        mockMvc.perform(post("/account/add")
                        .params(request))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertError"));
    }

    @Test
    public void postAddAccount_returnAddAccountForm_whenCalledWithInvalidArgument() throws Exception {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "");
        request.add("balance", "500");

        mockMvc.perform(post("/account/add")
                        .params(request))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/add"));
    }

    @Test
    public void deleteAccount_returnDeleteAccountForm() throws Exception {
        mockMvc.perform(get("/account/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/delete"));
    }

    @Test
    public void postDeleteAccount_returnMessageForm_whenCalledWithValidArgument() throws Exception {
        mockMvc.perform(post("/account/delete")
                    .param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    public void postDeleteAccount_returnDeleteAccountForm_whenCalledWithNull() throws Exception {
        mockMvc.perform(post("/account/delete")
                        .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/delete"));
    }

    @Test
    public void postDeleteAccount_returnDeleteAccountForm_whenCalledWithInvalidArgument() throws Exception {
        mockMvc.perform(post("/account/delete")
                        .param("id", "one"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/delete"));
    }
}