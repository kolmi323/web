package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.service.converter.web.ConverterTypeDTOToTypeResponse;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.personal.TypeService;
import ru.gnezdilov.web.interfaces.AuthorizationSessionTool;
import ru.gnezdilov.web.json.DeleteRequest;
import ru.gnezdilov.web.json.BooleanResponse;
import ru.gnezdilov.web.json.ListResponse;
import ru.gnezdilov.web.json.type.TypeResponse;
import ru.gnezdilov.web.json.type.add.TypeAddRequest;
import ru.gnezdilov.web.json.type.update.TypeUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/type")
public class TypeController implements AuthorizationSessionTool {
    private final TypeService typeService;
    private final ConverterTypeDTOToTypeResponse converter;

    @GetMapping("/show")
    public @ResponseBody ResponseEntity<ListResponse<TypeDTO>> show(HttpServletRequest request) {
        Integer userId = this.getUserIdFromSession(request);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TypeDTO> types = typeService.getAll(userId);
        return ok(new ListResponse<>(types));
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<TypeResponse> add(@RequestBody @Valid TypeAddRequest request,
                                                          HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        TypeDTO type = typeService.create(userId, request.getName());
        if (type == null) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ok(Objects.requireNonNull(converter.convert(type)));
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<BooleanResponse> delete(@RequestBody @Valid DeleteRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(new BooleanResponse(typeService.delete(request.getId(), userId)));
    }

    @PostMapping("/update")
    public @ResponseBody ResponseEntity<BooleanResponse> update(@RequestBody @Valid TypeUpdateRequest request,
                                                                   HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }

        return ok(new BooleanResponse(typeService.edit(request.getId(), userId, request.getNewName())));
    }
}
