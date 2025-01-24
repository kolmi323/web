package ru.gnezdilov.dao.model;

import ru.gnezdilov.dao.abstractclass.Model;

import java.util.Objects;

public class UserModel extends Model {
    private String name;
    private String email;
    private String password;

    public UserModel() {
    }

    public UserModel(int id, String name, String email, String password) {
        this.setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return this.getId() == userModel.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
