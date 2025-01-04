package ru.gnezdilov.service.personal;

import ru.gnezdilov.service.dto.TypeTransactionDTO;
import ru.gnezdilov.service.converter.ConverterTypeTransactionModelToTypeTransactionDTO;
import ru.gnezdilov.dao.TypeTransactionDAO;

import java.util.List;
import java.util.stream.Collectors;

public class TypeTransactionService {
    private final TypeTransactionDAO typeTransactionDAO;
    private final ConverterTypeTransactionModelToTypeTransactionDTO converter;

    public TypeTransactionService(TypeTransactionDAO dao, ConverterTypeTransactionModelToTypeTransactionDTO converter) {
        this.typeTransactionDAO = dao;
        this.converter = converter;
    }

    public List<TypeTransactionDTO> getAll(int userId) {
        return this.typeTransactionDAO.getAll(userId).stream()
                .map(this.converter::convert)
                .collect(Collectors.toList());
    }

    public TypeTransactionDTO edit(int userId, String oldName, String newName) {
        return this.converter
                .convert(this.typeTransactionDAO.update(userId, oldName, newName));
    }

    public TypeTransactionDTO create(int userId, String name) {
        return this.converter
                .convert(this.typeTransactionDAO.insert(userId, name));
    }

    public boolean delete(int id, int userId) {
        return this.typeTransactionDAO.delete(id, userId);
    }
}
