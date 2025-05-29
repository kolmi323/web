package ru.gnezdilov.web.json.account.show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.gnezdilov.service.dto.AccountDTO;
import ru.gnezdilov.web.abstractcustom.AbstractResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class AccountShowResponse extends AbstractResponse {
    private List<AccountDTO> accounts;
}
