package ru.gnezdilov.service.converter;

import ru.gnezdilov.service.dto.TypeTransactionDTO;
import ru.gnezdilov.dao.model.TypeTransactionModel;

public class ConverterTypeTransactionModelToTypeTransactionDTO
        implements Converter<TypeTransactionModel, TypeTransactionDTO> {
    @Override
    public TypeTransactionDTO convert(TypeTransactionModel source) {
        return new TypeTransactionDTO(source.getId(), source.getUserId(), source.getName());
    }
}
