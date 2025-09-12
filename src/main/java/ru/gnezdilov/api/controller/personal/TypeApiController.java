package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.AbstractController;
import ru.gnezdilov.api.controller.ApiController;
import ru.gnezdilov.api.converter.ConverterTypeDTOToTypeResponse;
import ru.gnezdilov.api.json.BooleanResponse;
import ru.gnezdilov.api.json.DeleteRequest;
import ru.gnezdilov.api.json.ListResponse;
import ru.gnezdilov.api.json.type.TypeResponse;
import ru.gnezdilov.api.json.type.add.TypeAddRequest;
import ru.gnezdilov.api.json.type.update.TypeUpdateRequest;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/type")
public class TypeApiController extends ApiController {
    private final TypeService typeService;
    private final ConverterTypeDTOToTypeResponse converter;

    @GetMapping("/show")
    public List<TypeDTO> show(HttpServletRequest request) {
        Integer userId = this.extractUserId(request);
        List<TypeDTO> types = typeService.getAll(userId);
        return types;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public TypeResponse add(@RequestBody @Valid TypeAddRequest request,
                                                          HttpServletRequest httpServletRequest) {
        Integer userId = this.extractUserId(httpServletRequest);
        TypeDTO type = typeService.create(userId, request.getName());
        return converter.convert(type);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody @Valid DeleteRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.extractUserId(httpServletRequest);
        return typeService.delete(request.getId(), userId);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody @Valid TypeUpdateRequest request,
                                                                   HttpServletRequest httpServletRequest) {
        Integer userId = this.extractUserId(httpServletRequest);
        return typeService.edit(request.getId(), userId, request.getNewName());
    }
}
