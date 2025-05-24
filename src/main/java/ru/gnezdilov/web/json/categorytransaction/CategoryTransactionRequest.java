package ru.gnezdilov.web.json.categorytransaction;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CategoryTransactionRequest {
    private LocalDate dateAfter;
    private LocalDate dateBefore;

    public void setDateAfter(String dateAfter) {
        this.dateAfter = LocalDate.parse(dateAfter);
    }

    public void setDateBefore(String dateBefore) {
        this.dateBefore = LocalDate.parse(dateBefore);
    }
}
