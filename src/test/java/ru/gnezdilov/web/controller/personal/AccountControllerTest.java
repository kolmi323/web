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
import org.springframework.util.MultiValueMap;
import ru.gnezdilov.MockSecurityConfiguration;
import ru.gnezdilov.WebApplication;
import ru.gnezdilov.config.SecurityConfiguration;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = AccountController.class)
@RunWith(SpringRunner.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = {WebApplication.class})
public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Before
    public void setUp() throws Exception {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(new AccountDTO(1, 1, "sber", new BigDecimal(1000)));

        AccountDTO accountDTO = new AccountDTO(2, 1, "T", new BigDecimal(500));

        when(accountService.create(1, "T", new BigDecimal(500))).thenReturn(accountDTO);
        when(accountService.getAll(1)).thenReturn(accountDTOList);
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void account_returnMainAccountForm() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/main"));
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void showAccount_returnAccountShowForm() throws Exception {
        mockMvc.perform(get("/account/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/show"));
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getAddAccount_returnAddAccountForm() throws Exception {
        mockMvc.perform(get("/account/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/add"));
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
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
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
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
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void deleteAccount_returnDeleteAccountForm() throws Exception {
        mockMvc.perform(get("/account/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/delete"));
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postDeleteAccount_returnMessageForm_whenCalledWithValidArgument() throws Exception {
        mockMvc.perform(post("/account/delete")
                    .param("id", "1"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postDeleteAccount_returnDeleteAccountForm_whenCalledWithInvalidArgument() throws Exception {
        mockMvc.perform(post("/account/delete")
                        .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/account/delete"));
    }
}