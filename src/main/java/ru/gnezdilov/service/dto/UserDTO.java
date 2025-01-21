package ru.gnezdilov.service.dto;

import ru.gnezdilov.dao.abstractclass.DTO;

import java.util.Objects;

public class UserDTO extends DTO {
    private String name;
    private String email;

    public UserDTO(int id, String name, String email) {
        this.setId(id);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id && Objects.equals(name, userDTO.name) && Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
