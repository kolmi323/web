package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.entities.CategoryTransactionModel;
import org.springframework.core.convert.converter.Converter;
import ru.gnezdilov.service.dto.CategoryTransactionDTO;

@Service
public class ConverterCategoryTransactionModelToCategoryTransactionDTO implements Converter<CategoryTransactionModel, CategoryTransactionDTO> {
    @Override
    public CategoryTransactionDTO convert(CategoryTransactionModel source) {
        return new CategoryTransactionDTO(source.getId(), source.getId(), source.getId());
    }
}
