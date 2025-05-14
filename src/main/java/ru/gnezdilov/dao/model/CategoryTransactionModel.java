package ru.gnezdilov.dao.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.Model;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryTransactionModel extends Model {
    private int typeId;
    private int transactionId;

    public CategoryTransactionModel(int id, int typeId, int transactionId) {
        this.setId(id);
        this.typeId = typeId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
