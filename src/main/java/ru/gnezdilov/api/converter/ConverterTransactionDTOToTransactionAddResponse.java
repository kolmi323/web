package ru.gnezdilov.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.gnezdilov.service.dto.TransactionDTO;
import ru.gnezdilov.api.json.transaction.add.TransactionAddResponse;

@Service
public class ConverterTransactionDTOToTransactionAddResponse implements Converter<TransactionDTO, TransactionAddResponse> {
    @Override
    public TransactionAddResponse convert(TransactionDTO source) {
        return new TransactionAddResponse(source.getId(), source.getAmount());
    }
}
