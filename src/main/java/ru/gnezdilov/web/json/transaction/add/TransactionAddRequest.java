package ru.gnezdilov.web.json.transaction.add;

import lombok.Getter;
import ru.gnezdilov.web.AbstractRequest;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
public class TransactionAddRequest extends AbstractRequest {
    private Integer[] typesIds;
    private int sendingId;
    private int receivingId;
    private BigDecimal amount;

    public void setTypesIds(String[] typesIds) {
            this.typesIds = extractIds(typesIds);
    }

    public void setSendingId(String sendingId) {
        this.sendingId = this.extractInt("sendingId", sendingId);
    }

    public void setReceivingId(String receivingId) {
        this.receivingId = this.extractInt("receivingId", receivingId);
    }

    public void setAmount(String amount) {
        this.amount = this.extractBigDecimal("amount", amount);
    }

    private Integer[] extractIds(String[] ids) {
        try {
            return Arrays.stream(ids).map(id -> this.extractInt("typeId", id)).toArray(Integer[]::new);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value in not valid" +
                    "\nExample: 1234");
        }
    }
}
