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
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TypeController.class)
@RunWith(SpringRunner.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
public class TypeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TypeService typeService;

    @Before
    public void setUp() throws Exception {
        List<TypeDTO> typeDTOS = new ArrayList<TypeDTO>();
        typeDTOS.add(new TypeDTO(1, 1, "work"));
        typeDTOS.add(new TypeDTO(2, 1, "hobby"));

        when(typeService.getAll(1)).thenReturn(typeDTOS);
        when(typeService.create(1, "candy")).thenReturn(new TypeDTO(3, 1, "candy"));
        when(typeService.edit(3, 1, "meat")).thenReturn(new TypeDTO(3, 1, "meat"));
        when(typeService.delete(3, 1)).thenReturn(true);
        when(typeService.delete(4, 1)).thenReturn(false);
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void type_returnMainTypeForm() throws Exception {
        mockMvc.perform(get("/type"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/main"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void showTypes_returnShowTypeForm() throws Exception {
        mockMvc.perform(get("/type/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/show"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getAddType_returnTypeAddForm() throws Exception {
        mockMvc.perform(get("/type/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/add"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postAddType_redirectToMessageForm_whenCalledWithValidArguments() throws Exception {
        mockMvc.perform(post("/type/add")
                        .param("name", "candy"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postAddType_returnTypeAddForm_whenCalledWithInvalidArguments() throws Exception {
        mockMvc.perform(post("/type/add")
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/add"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getUpdateType_returnTypeUpdateForm() throws Exception {
        mockMvc.perform(get("/type/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/update"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postUpdateType_redirectToMessageForm_whenCalledWithValidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "3");
        params.add("newName", "meat");

        mockMvc.perform(post("/type/update")
                    .params(params))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postUpdateType_returnTypeUpdateForm_whenCalledWithInvalidArguments() throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "3");
        params.add("newName", "");

        mockMvc.perform(post("/type/update")
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/update"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void getDeleteType_returnTypeDeleteForm() throws Exception {
        mockMvc.perform(get("/type/delete"))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/delete"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postDeleteType_redirectToMessageForm_whenTrueAndCalledWithValidArguments() throws Exception {
        mockMvc.perform(post("/type/delete")
                    .param("id", "3"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postDeleteType_redirectToMessageForm_whenFalseAndCalledWithValidArguments() throws Exception {
        mockMvc.perform(post("/type/delete")
                        .param("id", "4"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/alertMessage"));
    }

    @Test
    @WithUserDetails(value = "john@mail.ru", userDetailsServiceBeanName = "userDetailsService")
    public void postDeleteType_returnTypedDeleteForm_whenCalledWithInvalidArguments() throws Exception {
        mockMvc.perform(post("/type/delete")
                        .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/type/delete"));
    }
}