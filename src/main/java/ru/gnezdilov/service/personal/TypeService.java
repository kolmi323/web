package ru.gnezdilov.service.personal;

import ru.gnezdilov.service.dto.TypeDTO;
import ru.gnezdilov.service.converter.ConverterTypeModelToTypeDTO;
import ru.gnezdilov.dao.TypeDAO;

import java.util.List;
import java.util.stream.Collectors;

public class TypeService {
    private final TypeDAO typeDAO;
    private final ConverterTypeModelToTypeDTO converter;

    public TypeService(TypeDAO dao, ConverterTypeModelToTypeDTO converter) {
        this.typeDAO = dao;
        this.converter = converter;
    }

    public List<TypeDTO> getAll(int userId) {
        return this.typeDAO.getAll(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public boolean existsById(int id, int userId) {
        return typeDAO.existsById(userId, id);
    }

    public TypeDTO edit(int id, int userId, String newName) {
        return this.converter
                .convert(this.typeDAO.update(id, userId, newName));
    }

    public TypeDTO create(int userId, String name) {
        return this.converter
                .convert(this.typeDAO.insert(userId, name));
    }

    public boolean delete(int id, int userId) {
        return this.typeDAO.delete(id, userId);
    }
}
