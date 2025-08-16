package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import org.springframework.core.convert.converter.Converter;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.dao.entities.TypeModel;

@Service
public class ConverterTypeModelToTypeDTO implements Converter<TypeModel, TypeDTO> {
    @Override
    public TypeDTO convert(TypeModel source) {
        return new TypeDTO(source.getId(), source.getUserId(), source.getName());
    }
}
