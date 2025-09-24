package ru.gnezdilov.web.form.report;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReportDataForm {
    @NotEmpty
    private String dateAfter;
    @NotEmpty
    private String dateBefore;
}
