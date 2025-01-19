package ru.gnezdilov.service.custominterface;

public interface Converter <S,T> {
    T convert(S source);
}
