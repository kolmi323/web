package ru.gnezdilov.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteForm {
    @NotNull
    private Integer id;
}
