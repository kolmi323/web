package ru.gnezdilov.service.dto;

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
        return "Название типа транзакции: " + this.name;
    }
}
