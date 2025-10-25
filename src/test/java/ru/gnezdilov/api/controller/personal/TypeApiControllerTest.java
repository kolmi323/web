package ru.gnezdilov.api.controller.personal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import ru.gnezdilov.AbstractControllerTest;
import ru.gnezdilov.api.converter.ConverterTypeDTOToTypeResponse;
import ru.gnezdilov.api.json.type.TypeResponse;
import ru.gnezdilov.dao.exception.NotFoundException;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TypeApiController.class)
public class TypeApiControllerTest extends AbstractControllerTest {
    @MockBean
    private TypeService typeService;

    @SpyBean
    private ConverterTypeDTOToTypeResponse converter;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        List<TypeDTO> typeDTOS = new ArrayList<>();
        typeDTOS.add(new TypeDTO(1, 1, "work"));
        typeDTOS.add(new TypeDTO(2, 1, "hobby"));

        when(typeService.getAll(1)).thenReturn(typeDTOS);
        when(typeService.create(1, "candy")).thenReturn(new TypeDTO(3, 1, "candy"));
        when(typeService.edit(3, 1, "meat")).thenReturn(new TypeDTO(3, 1, "meat"));
        when(typeService.edit(4, 1, "meat")).thenThrow(NotFoundException.class);
        when(typeService.delete(3, 1)).thenReturn(true);
        when(typeService.delete(4, 1)).thenThrow(NotFoundException.class);
    }

    @Test
    public void getShow_returnJsonListType_whenCalled_withValidArguments() throws Exception {
        List<TypeDTO> response = new ArrayList<>();
        response.add(new TypeDTO(1, 1, "work"));
        response.add(new TypeDTO(2, 1, "hobby"));

        mockMvc.perform(get("/api/type/show"))
                .andExpect(content().json(ow.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    public void postAdd_returnJsonTypeResponse_whenCalledWithValidArgument() throws Exception {
        TypeResponse typeResponse = new TypeResponse(3, "candy");

        mockMvc.perform(post("/api/type/add")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\"name\" : \"candy\"}"))
                .andExpect(content().json(ow.writeValueAsString(typeResponse)))
                .andExpect(status().isCreated());
    }

    @Test
    public void postAdd_return400_whenCalledWithNullArgument() throws Exception {
        mockMvc.perform(post("/api/type/add")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"name\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postDelete_returnTrue_whenCalledWithValidArguments() throws Exception {
        mockMvc.perform(post("/api/type/delete")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\"id\" : \"3\"}"))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void postDelete_return404_whenTypeNotFound() throws Exception {
        mockMvc.perform(post("/api/type/delete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"4\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postDelete_return400_whenCalledWithNullArguments() throws Exception {
        mockMvc.perform(post("/api/type/delete")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdate_returnJsonTypeResponse_whenCalledWithValidArguments() throws Exception {
        TypeResponse typeResponse = new TypeResponse(3, "meat");

        mockMvc.perform(post("/api/type/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"3\", " +
                                "\"newName\" : \"meat\"}"))
                .andExpect(content().json(ow.writeValueAsString(typeResponse)))
                .andExpect(status().isOk());
    }

    @Test
    public void postUpdate_return400_whenCalledWithNullArgument() throws Exception {
        mockMvc.perform(post("/api/type/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"\", " +
                                "\"newName\" : \"meat\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdate_return400_whenCalledWithWrongTypeArgument() throws Exception {
        mockMvc.perform(post("/api/type/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"one\", " +
                                "\"newName\" : \"meat\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUpdate_return404_whenTypeNotFound() throws Exception {
        mockMvc.perform(post("/api/type/update")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"id\" : \"4\", " +
                                "\"newName\" : \"meat\"}"))
                .andExpect(status().isNotFound());
    }
}