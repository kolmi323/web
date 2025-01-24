package ru.gnezdilov.service.converter;

import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.dao.model.TypeModel;

public class ConverterTypeModelToTypeDTO
        implements Converter<TypeModel, TypeDTO> {
    @Override
    public TypeDTO convert(TypeModel source) {
        if (source == null) {
            return null;
        }
        return new TypeDTO(source.getId(), source.getUserId(), source.getName());
    }
}
