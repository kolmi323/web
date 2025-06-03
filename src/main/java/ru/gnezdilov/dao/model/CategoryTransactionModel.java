package ru.gnezdilov.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.service.custominterface.HasId;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryTransactionModel implements HasId {
    private int typeId;
    private int id;
    private int transactionId;

    public CategoryTransactionModel(int id, int typeId, int transactionId) {
        this.id = id;
        this.typeId = typeId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
