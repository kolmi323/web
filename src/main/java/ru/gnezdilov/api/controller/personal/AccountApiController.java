package ru.gnezdilov.api.controller.personal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gnezdilov.api.controller.ApiController;
import ru.gnezdilov.api.converter.ConverterAccountDTOToAccountAddResponse;
import ru.gnezdilov.api.json.DeleteRequest;
import ru.gnezdilov.api.json.account.create.AccountAddRequest;
import ru.gnezdilov.api.json.account.create.AccountAddResponse;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.service.personal.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountApiController extends ApiController {
    private final AccountService accountService;
    private final ConverterAccountDTOToAccountAddResponse converter;

    @GetMapping("/show")
    public List<AccountDTO> show() {
        Integer userId = this.currentUser().getId();
        return accountService.getAll(userId);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody @Valid DeleteRequest request) {
        Integer userId = this.currentUser().getId();
        accountService.delete(request.getId(), userId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountAddResponse add(@RequestBody @Valid AccountAddRequest request) {
        Integer userId = this.currentUser().getId();
        AccountDTO account = accountService.create(userId, request.getName(), request.getBalance());
        return converter.convert(account);
    }
}
