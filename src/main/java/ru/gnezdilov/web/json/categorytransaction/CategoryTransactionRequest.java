package ru.gnezdilov.web.json.categorytransaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.gnezdilov.web.AbstractRequest;

import java.time.LocalDate;

@Getter
public class CategoryTransactionRequest extends AbstractRequest {
    private LocalDate dateAfter;
    private LocalDate dateBefore;

    public void setDateAfter(String dateAfter) {
        this.dateAfter = this.extractLocalDate("dateAfter", dateAfter);
    }

    public void setDateBefore(String dateBefore) {
        this.dateBefore = this.extractLocalDate("dateBefore", dateBefore);
    }
}
