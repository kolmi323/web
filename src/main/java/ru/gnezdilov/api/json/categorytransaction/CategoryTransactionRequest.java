package ru.gnezdilov.api.json.categorytransaction;

import lombok.Getter;
import lombok.Setter;
import ru.gnezdilov.api.interfaces.AbstractRequest;

import java.time.LocalDate;

@Getter
@Setter
public class CategoryTransactionRequest implements AbstractRequest {
    private LocalDate dateAfter;
    private LocalDate dateBefore;
}
