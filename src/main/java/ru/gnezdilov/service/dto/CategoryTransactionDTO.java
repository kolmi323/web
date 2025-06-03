package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryTransactionDTO implements HasId {
    private int id;
    private int typeId;
    private int transactionId;

    public CategoryTransactionDTO(int id, int typeId, int transactionId) {
        this.id = id;
        this.typeId = typeId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
