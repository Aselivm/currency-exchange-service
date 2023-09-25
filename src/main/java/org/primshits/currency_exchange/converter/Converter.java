package org.primshits.currency_exchange.converter;

public interface Converter <S, T>{
    T convert(S source);
}
