package ru.gnezdilov.service.converter;

import org.springframework.stereotype.Service;
import ru.gnezdilov.dao.model.TransactionModel;
import ru.gnezdilov.service.custominterface.Converter;
import ru.gnezdilov.service.dto.TransactionDTO;

@Service
public class ConverterTransactionModelToTransactionDTO implements Converter<TransactionModel, TransactionDTO> {
    @Override
    public TransactionDTO convert(TransactionModel source) {
        if (source == null) {
            return null;
        }
        return new TransactionDTO(source.getId(), source.getSenderAccountId(), source.getReceiverAccountId(),
                source.getAmount(), source.getDate());
    }
}
