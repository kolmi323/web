package ru.gnezdilov.service.converter;

public interface Converter <S,T> {
    T convert(S source);
}
