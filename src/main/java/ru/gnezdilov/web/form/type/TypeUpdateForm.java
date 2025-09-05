package ru.gnezdilov.web.form.type;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TypeUpdateForm {
    @NotNull
    private Integer id;
    @NotEmpty
    private String newName;
}
