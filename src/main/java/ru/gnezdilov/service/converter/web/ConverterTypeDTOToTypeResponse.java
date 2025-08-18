package ru.gnezdilov.service.converter.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.web.json.type.TypeResponse;

@Service
public class ConverterTypeDTOToTypeResponse implements Converter<TypeDTO, TypeResponse> {
    @Override
    public TypeResponse convert(TypeDTO source) {
        return new TypeResponse(source.getId(), source.getName());
    }
}
