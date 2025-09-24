package ru.gnezdilov.web.form.type;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TypeAddForm {
    @NotEmpty
    private String name;
}
