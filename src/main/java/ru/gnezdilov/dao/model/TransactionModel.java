package ru.gnezdilov.dao.model;

import ru.gnezdilov.dao.abstractclass.Model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class TransactionModel extends Model {
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private Date date;

    public TransactionModel() {
    }

    public TransactionModel(int id, int senderAccountId, int receiverAccountId, BigDecimal amount, Date date) {
        this.setId(id);
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.date = date;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public int getReceiverAccountId() {
        return receiverAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public void setReceiverAccountId(int receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id + ". " + date +": " + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionModel that = (TransactionModel) o;
        return senderAccountId == that.senderAccountId && receiverAccountId == that.receiverAccountId && Objects.equals(amount, that.amount) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderAccountId, receiverAccountId, amount, date);
    }
}
