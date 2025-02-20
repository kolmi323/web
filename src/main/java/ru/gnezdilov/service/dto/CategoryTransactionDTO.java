package ru.gnezdilov.service.dto;

import ru.gnezdilov.dao.abstractclass.DTO;

import java.util.Objects;

public class CategoryTransactionDTO extends DTO {
    private int typeId;
    private int transactionId;

    public CategoryTransactionDTO(int id, int typeId, int transactionId) {
        this.id = id;
        this.typeId = typeId;
        this.transactionId = transactionId;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryTransactionDTO that = (CategoryTransactionDTO) o;
        return typeId == that.typeId && transactionId == that.transactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, transactionId);
    }
}
