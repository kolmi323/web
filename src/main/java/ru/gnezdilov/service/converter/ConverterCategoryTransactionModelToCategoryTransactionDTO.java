package ru.gnezdilov.service.converter;

import ru.gnezdilov.dao.model.CategoryTransactionModel;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.CategoryTransactionDTO;

public class ConverterCategoryTransactionModelToCategoryTransactionDTO implements Converter<CategoryTransactionModel, CategoryTransactionDTO> {
    @Override
    public CategoryTransactionDTO convert(CategoryTransactionModel source) {
        return new CategoryTransactionDTO(source.getId(), source.getTypeId(), source.getTransactionId());
    }
}
