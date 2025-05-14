package ru.gnezdilov.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gnezdilov.dao.abstractclass.DTO;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryTransactionDTO extends DTO {
    private int typeId;
    private int transactionId;

    public CategoryTransactionDTO(int id, int typeId, int transactionId) {
        this.setId(id);
        this.typeId = typeId;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
