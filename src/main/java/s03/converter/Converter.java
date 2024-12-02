package s03.converter;

public interface Converter <S,T> {
    T convert(S source);
}
