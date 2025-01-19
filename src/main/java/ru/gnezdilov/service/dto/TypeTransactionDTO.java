package ru.gnezdilov.service.dto;

import java.util.Objects;

public class TypeTransactionDTO {
    private int id;
    private int userId;
    private String name;

    public TypeTransactionDTO() {
    }

    public TypeTransactionDTO(int id, int userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.id + ". Название типа транзакции: " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeTransactionDTO that = (TypeTransactionDTO) o;
        return id == that.id && userId == that.userId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name);
    }
}
