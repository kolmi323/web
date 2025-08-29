package ru.gnezdilov.web.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.service.converter.web.ConverterAccountDTOToAccountAddResponse;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;
import ru.gnezdilov.web.interfaces.AuthorizationSessionTool;
import ru.gnezdilov.web.json.DeleteRequest;
import ru.gnezdilov.web.json.BooleanResponse;
import ru.gnezdilov.web.json.ListResponse;
import ru.gnezdilov.web.json.account.create.AccountAddRequest;
import ru.gnezdilov.web.json.account.create.AccountAddResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController implements AuthorizationSessionTool {
    private final AccountService accountService;
    private final ConverterAccountDTOToAccountAddResponse converter;

    @GetMapping("/show")
    public @ResponseBody ResponseEntity<ListResponse<AccountDTO>> show(HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        List<AccountDTO> accounts = accountService.getAll(userId);
        return ok(new ListResponse<>(accounts));
    }

    @PostMapping("/delete")
    public @ResponseBody ResponseEntity<BooleanResponse> delete(@RequestBody @Valid DeleteRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(new BooleanResponse(accountService.delete(request.getId(), userId)));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<AccountAddResponse> add(@RequestBody @Valid AccountAddRequest request,
                                                                HttpServletRequest httpServletRequest) {
        Integer userId = this.getUserIdFromSession(httpServletRequest);
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
