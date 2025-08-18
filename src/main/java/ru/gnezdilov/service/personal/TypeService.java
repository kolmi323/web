package ru.gnezdilov.service.personal;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.entities.TypeModel;
import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.dao.TypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {
    private final TypeRepository typeRepository;
    private final ConverterTypeModelToTypeDTO converter;

    public TypeService(TypeRepository dao, ConverterTypeModelToTypeDTO converter) {
        this.typeRepository = dao;
        this.converter = converter;
    }

    public List<TypeDTO> getAll(int userId) {
        return this.typeRepository.getAllByUserId(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public boolean existsById(int id, int userId) {
        return typeRepository.existsByIdAndUserId(id, userId);
    }

    public boolean edit(int id, int userId, String newName) {
        return this.typeRepository.updateName(id, userId, newName) == 1;
    }

    public TypeDTO create(int userId, String name) {
        TypeModel type = new TypeModel();
        type.setUserId(userId);
        type.setName(name);
        return this.converter
                .convert(this.typeRepository.save(type));
    }

    public boolean delete(int id, int userId) {
        return this.typeRepository.deleteByIdAndUserId(id, userId) == 1;
    }
}
