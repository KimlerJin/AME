package com.ame.spring.converters;

import org.springframework.core.convert.converter.Converter;

public class Number2BooleanConverter implements Converter<Number, Boolean> {

    @Override
    public Boolean convert(Number source) {
        return Boolean.valueOf(Integer.toString(source.intValue()));
    }
}
