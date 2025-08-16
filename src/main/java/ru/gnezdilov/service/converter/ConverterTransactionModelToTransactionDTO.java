package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.entities.TransactionModel;
import org.springframework.core.convert.converter.Converter;
import ru.gnezdilov.service.dto.TransactionDTO;

@Service
public class ConverterTransactionModelToTransactionDTO implements Converter<TransactionModel, TransactionDTO> {
    @Override
    public TransactionDTO convert(TransactionModel source) {
        return new TransactionDTO(source.getId(), source.getSenderAccountId(), source.getReceiverAccountId(),
                source.getAmount(), source.getDate());
    }
}
