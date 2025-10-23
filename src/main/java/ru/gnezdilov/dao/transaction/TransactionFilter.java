package ru.gnezdilov.dao.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionFilter {
    private Integer userId;
    private LocalDate dateAfter;
    private LocalDate dateBefore;
    private boolean isIncoming;
}
