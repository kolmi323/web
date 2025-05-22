package ru.gnezdilov.web.json.type.update;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TypeUpdateResponse {
    private int id;
    private String newName;
}
