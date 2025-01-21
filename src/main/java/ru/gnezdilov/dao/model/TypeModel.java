package ru.gnezdilov.dao.model;

import ru.gnezdilov.dao.abstractclass.Model;

import java.util.Objects;

public class TypeModel extends Model {
    private int userId;
    private String name;

    public TypeModel() {
    }

    public TypeModel(int id, int userId, String name) {
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
        return "Название типа транзакции: " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeModel that = (TypeModel) o;
        return userId == that.userId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name);
    }
}
