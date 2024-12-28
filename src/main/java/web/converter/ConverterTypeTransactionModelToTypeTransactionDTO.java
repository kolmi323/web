package web.converter;

import web.dao.Model.TypeTransactionModel;
import web.service.DTO.TypeTransactionDTO;

public class ConverterTypeTransactionModelToTypeTransactionDTO
        implements Converter<TypeTransactionModel, TypeTransactionDTO> {
    @Override
    public TypeTransactionDTO convert(TypeTransactionModel source) {
        return new TypeTransactionDTO(source.getUserId(), source.getName());
    }
}
