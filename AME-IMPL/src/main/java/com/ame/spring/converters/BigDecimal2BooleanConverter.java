package com.ame.spring.converters;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class BigDecimal2BooleanConverter implements Converter<BigDecimal, Boolean> {

    @Override
    public Boolean convert(BigDecimal source) {
        return Boolean.valueOf(Integer.toString(source.intValue()));
    }
}
