package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.ApiController;
import ru.gnezdilov.api.converter.ConverterAccountDTOToAccountAddResponse;
import ru.gnezdilov.api.json.BooleanResponse;
import ru.gnezdilov.api.json.DeleteRequest;
import ru.gnezdilov.api.json.ListResponse;
import ru.gnezdilov.api.json.account.create.AccountAddRequest;
import ru.gnezdilov.api.json.account.create.AccountAddResponse;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController extends ApiController {
    private final AccountService accountService;
    private final ConverterAccountDTOToAccountAddResponse converter;

    @GetMapping("/show")
    public ResponseEntity<ListResponse<AccountDTO>> show(HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<AccountDTO> accounts = accountService.getAll(userId);
        return ok(new ListResponse<>(accounts));
    }

    @PostMapping("/delete")
    public ResponseEntity<BooleanResponse> delete(@RequestBody @Valid DeleteRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(new BooleanResponse(accountService.delete(request.getId(), userId)));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountAddResponse> add(@RequestBody @Valid AccountAddRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.pullUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        AccountDTO account = accountService.create(userId, request.getName(), request.getBalance());
        if (account == null) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ok(Objects.requireNonNull(converter.convert(account)));
    }
}
