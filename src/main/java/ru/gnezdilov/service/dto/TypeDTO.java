package ru.gnezdilov.service.dto;

import ru.gnezdilov.dao.abstractclass.DTO;

import java.util.Objects;

public class TypeDTO extends DTO {
    private int userId;
    private String name;

    public TypeDTO() {
    }

    public TypeDTO(int id, int userId, String name) {
        this.setId(id);
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
        return this.id + ". " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDTO that = (TypeDTO) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
