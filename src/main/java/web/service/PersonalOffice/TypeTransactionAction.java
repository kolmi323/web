package web.service.PersonalOffice;

import web.converter.ConverterTypeTransactionModelToTypeTransactionDTO;
import web.dao.DaoFactory;
import web.service.DTO.TypeTransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TypeTransactionAction {
    private final ConverterTypeTransactionModelToTypeTransactionDTO converterTypeTransactionModelToTypeTransactionDTO;

    public TypeTransactionAction() {
        this.converterTypeTransactionModelToTypeTransactionDTO = new ConverterTypeTransactionModelToTypeTransactionDTO();
    }

    public List<TypeTransactionDTO> getAll(int userId) {
        return DaoFactory.getTypeTransactionDao().getListTypeTransaction(userId).stream()
                .map(this.converterTypeTransactionModelToTypeTransactionDTO::convert).collect(Collectors.toList());
    }

    public TypeTransactionDTO edit(int userId, String oldName, String newName) {
        return this.converterTypeTransactionModelToTypeTransactionDTO
                .convert(DaoFactory.getTypeTransactionDao().updateType(userId, oldName, newName));
    }

    public TypeTransactionDTO create(int userId, String name) {
        return this.converterTypeTransactionModelToTypeTransactionDTO
                .convert(DaoFactory.getTypeTransactionDao().insertType(userId, name));
    }

    public boolean delete(int userId, String name) {
        return DaoFactory.getTypeTransactionDao().deleteType(userId, name);
    }
}
