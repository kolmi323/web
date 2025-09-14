package ru.gnezdilov.api.json;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteRequest {
    @NotNull
    private Integer id;
}
