package ru.gnezdilov.web.json.type.update;

import lombok.Data;

@Data
public class TypeUpdateRequest {
    private int id;
    private String newName;
}
